package vms.repositories;

import org.springframework.data.repository.CrudRepository;
import vms.models.ProxyServer;

public interface ProxyServerRepository extends CrudRepository<ProxyServer, Long> {
	ProxyServer getProxyServerByLogin(String login);
}
