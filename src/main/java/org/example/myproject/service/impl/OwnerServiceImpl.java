package org.example.myproject.service.impl;

import org.example.myproject.model.Owner;
import org.example.myproject.repository.OwnerRepository;
import org.example.myproject.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    public List<Owner> findAll() {
        return ownerRepository.findAll();
    }

    @Override
    public Optional<Owner> findById(long id) {
        return ownerRepository.findById(id);
    }

    @Override
    public Owner save(Owner owner) {
        return ownerRepository.save(owner);
    }

    @Override
    public void delete(long id) {
        ownerRepository.deleteById(id);
    }

    @Override
    public Owner acceptOwner(long id) {
        Optional<Owner> owner = ownerRepository.findById(id);
        if (owner.isPresent()) {
            Owner own = owner.get();
            own.setStatus(true);
            own.setState(true);
            return ownerRepository.save(own);
        }
        return null;
    }
}
