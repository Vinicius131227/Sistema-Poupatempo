package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Servico;

public class ServicoDao {

    public List<Servico> listarTodos() throws SQLException {
        List<Servico> lista = new ArrayList<>();
        String sql = "SELECT id, nome, sigla, descricao FROM servico ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Servico s = new Servico(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("sigla"),
                    rs.getString("descricao")
                );
                lista.add(s);
            }
        }
        return lista;
    }

    public Servico buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, nome, sigla, descricao FROM servico WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Servico(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("sigla"),
                        rs.getString("descricao")
                    );
                }
            }
        }
        return null;
    }
}