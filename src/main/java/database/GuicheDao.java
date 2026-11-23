package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Guiche;
import model.Servico;

public class GuicheDao {

    public List<Guiche> listarTodos() throws SQLException {
        List<Guiche> guiches = new ArrayList<>();
        String sql = "SELECT id, numero, ocupado, contador_prioridade_consecutiva FROM guiche ORDER BY numero";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Guiche g = new Guiche();
                g.setId(rs.getInt("id"));
                g.setNumero(rs.getInt("numero"));
                g.setOcupado(rs.getBoolean("ocupado"));
                g.setContadorPrioridadeConsecutiva(rs.getInt("contador_prioridade_consecutiva"));
                g.setServicos(listarServicosDoGuiche(conn, g.getId()));
                guiches.add(g);
            }
        }
        return guiches;
    }

    public Guiche buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, numero, ocupado, contador_prioridade_consecutiva FROM guiche WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Guiche g = new Guiche();
                    g.setId(rs.getInt("id"));
                    g.setNumero(rs.getInt("numero"));
                    g.setOcupado(rs.getBoolean("ocupado"));
                    g.setContadorPrioridadeConsecutiva(rs.getInt("contador_prioridade_consecutiva"));
                    g.setServicos(listarServicosDoGuiche(conn, g.getId()));
                    return g;
                }
            }
        }
        return null;
    }

    private List<Servico> listarServicosDoGuiche(Connection conn, int guicheId) throws SQLException {
        List<Servico> servicos = new ArrayList<>();
        String sql = "SELECT s.id, s.nome, s.sigla, s.descricao " +
                     "FROM servico s " +
                     "INNER JOIN guiche_servico gs ON s.id = gs.servico_id " +
                     "WHERE gs.guiche_id = ? ORDER BY s.id";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, guicheId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Servico s = new Servico(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("sigla"),
                        rs.getString("descricao")
                    );
                    servicos.add(s);
                }
            }
        }
        return servicos;
    }

    public void atualizarOcupado(int guicheId, boolean ocupado) throws SQLException {
        String sql = "UPDATE guiche SET ocupado = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, ocupado);
            stmt.setInt(2, guicheId);
            stmt.executeUpdate();
        }
    }

    public void atualizarContadorPrioridade(int guicheId, int contador) throws SQLException {
        String sql = "UPDATE guiche SET contador_prioridade_consecutiva = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, contador);
            stmt.setInt(2, guicheId);
            stmt.executeUpdate();
        }
    }
}