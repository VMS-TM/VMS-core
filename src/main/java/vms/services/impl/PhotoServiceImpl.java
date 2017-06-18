package vms.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vms.models.postenvironment.Photo;
import vms.repositories.PhotoRepository;
import vms.services.absr.PhotoService;

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
