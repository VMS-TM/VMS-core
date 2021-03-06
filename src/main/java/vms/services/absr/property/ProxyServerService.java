package vms.services.absr.property;

import vms.models.property.ProxyServer;

import java.util.List;

public interface ProxyServerService {
	void addProxyServer(ProxyServer proxyServer);

	void editProxyServer(ProxyServer proxyServer);

	List<ProxyServer> proxyServerList();

	ProxyServer getProxyServer(Long id);

	ProxyServer getProxyServer(String login);

	ProxyServer getProxyServerByToken(String token);

	void deleteProxyServer(Long id);

	List<ProxyServer> getProxyServerByDestiny(String destiny);

	List<ProxyServer> findBadProxy(Boolean work);
}
