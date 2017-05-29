package vms.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vms.models.ProxyServer;
import vms.repositories.ProxyServerRepository;
import vms.services.absr.ProxyServerService;

import java.util.List;

@Service
public class ProxyServerServiceImpl implements ProxyServerService {

	@Autowired
	private ProxyServerRepository repository;

	@Override
	public void addProxyServer(ProxyServer proxyServer) {
		repository.save(proxyServer);
	}

	@Override
	public void editProxyServer(ProxyServer proxyServer) {
		repository.save(proxyServer);
	}

	@Override
	public List<ProxyServer> proxyServerList() {
		return (List<ProxyServer>) repository.findAll();
	}

	@Override
	public ProxyServer getProxyServer(Long id) {
		return repository.findOne(id);
	}

	@Override
	public ProxyServer getProxyServer(String login) {
		return repository.getProxyServerByLogin(login);
	}

	@Override
	public void deleteProxyServer(Long id) {
		repository.delete(id);
	}
}
