package by.anpoliakov.service;

import by.anpoliakov.domain.entity.AuditLog;
import by.anpoliakov.domain.entity.User;
import by.anpoliakov.domain.enums.ActionType;

import java.util.List;
import java.util.Map;

public interface AuditLogService {
    void addAuditLog(User user, ActionType actionType);
    List<AuditLog> getAuditLogsByLogin(String loginUser);
}
