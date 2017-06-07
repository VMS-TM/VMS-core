package vms.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "proxy")
public class ProxyServer {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(min = 5, max = 30)
	@Pattern(regexp = "^[a-zA-Z0-9@._+]+$")
	@Column(name = "login", unique = true, nullable = false)
	private String login;

	@NotNull
	@Size(min = 5, max = 20)
	@Column(name = "password", nullable = false)
	private String password;

	@NotNull
	@Pattern(regexp = "^[a-zA-Z0-9]+$")
	@Column(name = "token", unique = true, nullable = false)
	private String token;

	@NotNull
	@Pattern(regexp = "^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$")
	@Column(name = "ip", unique = true, nullable = false)
	private String ip;

	@NotNull
	@Max(value = 65535)
	@Column(name = "port", nullable = false)
	private int port;

	public ProxyServer() {

	}

	public ProxyServer(String login, String password, String token, String ip, int port) {
		this.login = login;
		this.password = password;
		this.token = token;
		this.ip = ip;
		this.port = port;
	}

	public ProxyServer(Long id, String login, String password, String token, String ip, int port) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.token = token;
		this.ip = ip;
		this.port = port;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ProxyServer that = (ProxyServer) o;

		if (port != that.port) return false;
		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (login != null ? !login.equals(that.login) : that.login != null) return false;
		if (password != null ? !password.equals(that.password) : that.password != null) return false;
		if (token != null ? !token.equals(that.token) : that.token != null) return false;
		return ip != null ? ip.equals(that.ip) : that.ip == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (login != null ? login.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		result = 31 * result + (token != null ? token.hashCode() : 0);
		result = 31 * result + (ip != null ? ip.hashCode() : 0);
		result = 31 * result + port;
		return result;
	}
}
