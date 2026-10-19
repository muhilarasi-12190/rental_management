package com.rrms.service;

import com.rrms.model.Resource;
import com.rrms.repository.ResourceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {

    private final ResourceRepository resourceRepository;

    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public Resource addResource(Resource resource) {
        return resourceRepository.save(resource);
    }

    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    public Resource updateResource(Long id, Resource updatedResource) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found"));

        resource.setName(updatedResource.getName());
        resource.setCategory(updatedResource.getCategory());
        resource.setRentalPrice(updatedResource.getRentalPrice());

        return resourceRepository.save(resource);
    }

    public void deleteResource(Long id) {
        if (!resourceRepository.existsById(id)) {
            throw new RuntimeException("Resource not found");
        }

        resourceRepository.deleteById(id);
    }
}

