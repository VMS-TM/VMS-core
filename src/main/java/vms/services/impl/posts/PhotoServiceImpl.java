package vms.services.impl.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vms.models.posts.Photo;
import vms.repositories.posts.PhotoRepository;
import vms.services.absr.posts.PhotoService;

import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoService {

	@Autowired
	private PhotoRepository repository;


	@Override
	public void addPhotos(List<Photo> photos) {
		repository.save(photos);
	}
}
