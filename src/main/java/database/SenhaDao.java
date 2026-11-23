package database;

import dto.SenhaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.Senha;

public class SenhaDao {

    private final SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");

    public int contarTotalPorServicoHoje(int servicoId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM senha WHERE servico_id = ? AND DATE(dt_emissao) = CURRENT_DATE";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, servicoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public int contarAguardandoAFrente(int servicoId, boolean prioritaria) throws SQLException {
        String sql;
        if (prioritaria) {
            sql = "SELECT COUNT(*) FROM senha WHERE servico_id = ? AND status = 'AGUARDANDO' AND prioritaria = TRUE";
        } else {
            sql = "SELECT COUNT(*) FROM senha WHERE servico_id = ? AND status = 'AGUARDANDO'";
        }

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, servicoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public Senha inserir(Senha s) throws SQLException {
        String sql = "INSERT INTO senha (codigo, servico_id, prioritaria, status, dt_emissao) " +
                     "VALUES (?, ?, ?, 'AGUARDANDO', NOW()) RETURNING id";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, s.getCodigo());
            stmt.setInt(2, s.getServicoId());
            stmt.setBoolean(3, s.isPrioritaria());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    s.setId(rs.getInt("id"));
                }
            }
        }
        return s;
    }

    public List<SenhaDTO> listarAguardandoPorGuiche(int guicheId) throws SQLException {
        List<SenhaDTO> lista = new ArrayList<>();
        String sql = "SELECT s.*, srv.nome AS servico_nome " +
                     "FROM senha s " +
                     "INNER JOIN servico srv ON s.servico_id = srv.id " +
                     "INNER JOIN guiche_servico gs ON gs.servico_id = s.servico_id " +
                     "WHERE gs.guiche_id = ? AND s.status = 'AGUARDANDO' " +
                     "ORDER BY s.prioritaria DESC, s.dt_emissao ASC, s.id ASC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, guicheId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(montarDTO(rs, false));
                }
            }
        }
        return lista;
    }

    public SenhaDTO buscarAtivaDoGuiche(int guicheId) throws SQLException {
        String sql = "SELECT s.*, srv.nome AS servico_nome, g.numero AS guiche_numero " +
                     "FROM senha s " +
                     "INNER JOIN servico srv ON s.servico_id = srv.id " +
                     "INNER JOIN guiche g ON s.guiche_id = g.id " +
                     "WHERE s.guiche_id = ? AND s.status IN ('CHAMADA', 'EM ATENDIMENTO') " +
                     "LIMIT 1";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, guicheId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return montarDTO(rs, true);
                }
            }
        }
        return null;
    }

    public void registrarChamada(int senhaId, int guicheId) throws SQLException {
        String sql = "UPDATE senha SET status = 'CHAMADA', guiche_id = ?, dt_chamada = NOW() WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, guicheId);
            stmt.setInt(2, senhaId);
            stmt.executeUpdate();
        }
    }

    public void rechamar(int senhaId) throws SQLException {
        String sql = "UPDATE senha SET rechamadas = rechamadas + 1, dt_chamada = NOW() " +
                     "WHERE id = ? AND status = 'CHAMADA'";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, senhaId);
            stmt.executeUpdate();
        }
    }

    public void iniciarAtendimento(int senhaId) throws SQLException {
        String sql = "UPDATE senha SET status = 'EM ATENDIMENTO', dt_inicio = NOW() " +
                     "WHERE id = ? AND status = 'CHAMADA'";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, senhaId);
            stmt.executeUpdate();
        }
    }

    public void finalizarOuAusente(int senhaId, String novoStatus) throws SQLException {
        String sql = "UPDATE senha SET status = ?, dt_termino = NOW() WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novoStatus);
            stmt.setInt(2, senhaId);
            stmt.executeUpdate();
        }
    }

    public List<SenhaDTO> listarUltimasChamadas(int limite) throws SQLException {
        List<SenhaDTO> lista = new ArrayList<>();
        String sql = "SELECT s.*, srv.nome AS servico_nome, g.numero AS guiche_numero " +
                     "FROM senha s " +
                     "INNER JOIN servico srv ON s.servico_id = srv.id " +
                     "INNER JOIN guiche g ON s.guiche_id = g.id " +
                     "WHERE s.dt_chamada IS NOT NULL " +
                     "ORDER BY s.dt_chamada DESC, s.id DESC " +
                     "LIMIT ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, limite);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(montarDTO(rs, true));
                }
            }
        }
        return lista;
    }

    private SenhaDTO montarDTO(ResultSet rs, boolean temGuicheNumero) throws SQLException {
        SenhaDTO dto = new SenhaDTO();
        dto.setId(rs.getInt("id"));
        dto.setCodigo(rs.getString("codigo"));
        dto.setServicoNome(rs.getString("servico_nome"));
        dto.setPrioritaria(rs.getBoolean("prioritaria"));
        dto.setStatus(rs.getString("status"));
        dto.setRechamadas(rs.getInt("rechamadas"));

        if (temGuicheNumero) {
            dto.setGuicheNumero(rs.getInt("guiche_numero"));
        }

        Timestamp dtEmissao = rs.getTimestamp("dt_emissao");
        if (dtEmissao != null) {
            dto.setDtEmissao(formatoHora.format(dtEmissao));
        }

        Timestamp dtChamada = rs.getTimestamp("dt_chamada");
        if (dtChamada != null) {
            dto.setDtChamada(formatoHora.format(dtChamada));
        }

        return dto;
    }
}