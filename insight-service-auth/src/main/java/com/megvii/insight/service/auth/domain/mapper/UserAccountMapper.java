package com.megvii.insight.service.auth.domain.mapper;

import com.megvii.insight.service.auth.domain.UserAccount;
import com.megvii.insight.service.auth.domain.model.UserBean;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserAccountMapper {

  UserBean mapToUserBean(UserAccount account);

  @InheritInverseConfiguration
  UserAccount mapToAccount(UserBean userBean);
}
