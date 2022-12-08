package org.example.controller;

import java.util.List;
import java.util.Optional;
import org.example.model.IPProxy;
import org.example.repository.IPProxyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/proxy")
@CrossOrigin
public class IPProxyController {
    @Autowired
    private IPProxyRepository ipProxyRepository;

    @GetMapping
    public List<IPProxy> getAllProxy() {
        return ipProxyRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<IPProxy> addProxy(@RequestBody IPProxy proxy) {
        try {
            IPProxy newProxy = ipProxyRepository.save(proxy);
            return new ResponseEntity<>(newProxy, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<IPProxy> updateProxy(@PathVariable("id") Long id, @RequestBody IPProxy proxy) {
        Optional<IPProxy> findProxy = ipProxyRepository.findById(id);

        if (findProxy.isPresent()) {
            IPProxy newProxy = findProxy.get();

            newProxy.setAddress(proxy.getAddress());
            newProxy.setPort(proxy.getPort());
            newProxy.setStatus(proxy.getStatus());
            newProxy.setProtocol(proxy.getProtocol());

            return new ResponseEntity<>(ipProxyRepository.save(newProxy), HttpStatus.OK);
        } else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
