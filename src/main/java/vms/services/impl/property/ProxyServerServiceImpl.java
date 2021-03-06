package vms.services.impl.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vms.models.property.ProxyServer;
import vms.repositories.property.ProxyServerRepository;
import vms.services.absr.property.ProxyServerService;

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

	@Override
	public ProxyServer getProxyServerByToken(String token) {
		return repository.getProxyServerByToken(token);
	}

	@Override
	public List<ProxyServer> getProxyServerByDestiny(String destiny) {
		return repository.findAllByDestiny(destiny);
	}

	@Override
	public List<ProxyServer> findBadProxy(Boolean work) {
		return repository.findAllByWork(work);
	}
}
