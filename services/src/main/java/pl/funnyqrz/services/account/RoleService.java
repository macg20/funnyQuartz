package pl.funnyqrz.services.account;

import pl.funnyqrz.entities.account.RoleEntity;

import javax.validation.Valid;

public interface RoleService {

    RoleEntity save(@Valid RoleEntity role);

    RoleEntity findRoleByName(String name);
}
