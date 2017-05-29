package vms.services.absr;

import vms.models.ProxyServer;

import java.util.List;

public interface ProxyServerService {
	void addProxyServer(ProxyServer proxyServer);

	void editProxyServer(ProxyServer proxyServer);

	List<ProxyServer> proxyServerList();

	ProxyServer getProxyServer(Long id);

	ProxyServer getProxyServer(String login);

	void deleteProxyServer(Long id);
}
