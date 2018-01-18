package pl.funnyqrz.services.account;

import pl.funnyqrz.entities.account.Role;

import javax.validation.Valid;

public interface RoleService {

    Role save(@Valid Role role);

    Role findRoleByName(String name);
}
