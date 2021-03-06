package vms.models.property;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "proxy")
public class ProxyServer implements Serializable {
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
	private Integer port;

	@NotNull
	@Column(name = "destiny")
	private String destiny;

	private Boolean work;

	public Boolean getWork() {
		return work;
	}

	public void setWork(Boolean work) {
		this.work = work;
	}

	public ProxyServer() {

	}

	public ProxyServer(String login, String password, String token, String ip, Integer port, String destiny) {
		this.login = login;
		this.password = password;
		this.token = token;
		this.ip = ip;
		this.port = port;
		this.destiny = destiny;
	}

	public ProxyServer(Long id, String login, String password, String token, String ip, Integer port, String destiny) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.token = token;
		this.ip = ip;
		this.port = port;
		this.destiny = destiny;
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

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getDestiny() {
		return destiny;
	}

	public void setDestiny(String destiny) {
		this.destiny = destiny;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ProxyServer that = (ProxyServer) o;

		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (login != null ? !login.equals(that.login) : that.login != null) return false;
		if (password != null ? !password.equals(that.password) : that.password != null) return false;
		if (token != null ? !token.equals(that.token) : that.token != null) return false;
		if (ip != null ? !ip.equals(that.ip) : that.ip != null) return false;
		if (port != null ? !port.equals(that.port) : that.port != null) return false;
		return destiny != null ? destiny.equals(that.destiny) : that.destiny == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (login != null ? login.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		result = 31 * result + (token != null ? token.hashCode() : 0);
		result = 31 * result + (ip != null ? ip.hashCode() : 0);
		result = 31 * result + (port != null ? port.hashCode() : 0);
		result = 31 * result + (destiny != null ? destiny.hashCode() : 0);
		return result;
	}
}
