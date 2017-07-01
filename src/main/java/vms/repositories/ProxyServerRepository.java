package vms.repositories;

import org.springframework.data.repository.CrudRepository;
import vms.models.ProxyServer;

import java.util.List;

public interface ProxyServerRepository extends CrudRepository<ProxyServer, Long> {
	ProxyServer getProxyServerByLogin(String login);

	ProxyServer getProxyServerByToken(String token);

	List<ProxyServer> findAllByDestiny(String destiny);

	List<ProxyServer> findAllByWork(Boolean work);
}
