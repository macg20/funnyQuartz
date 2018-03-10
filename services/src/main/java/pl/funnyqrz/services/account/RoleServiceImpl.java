package pl.funnyqrz.services.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.funnyqrz.entities.account.RoleEntity;
import pl.funnyqrz.repositories.RoleRepository;
import pl.funnyqrz.services.AbstractService;

import javax.validation.Valid;

@Service
public class RoleServiceImpl extends AbstractService implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public RoleEntity save(@Valid RoleEntity role) {
        return roleRepository.save(role);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleEntity findRoleByName(String name) {
        return roleRepository.findByName(name);
    }


}
