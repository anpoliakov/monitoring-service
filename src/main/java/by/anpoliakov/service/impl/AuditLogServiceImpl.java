package by.anpoliakov.service.impl;

import by.anpoliakov.domain.entity.AuditLog;
import by.anpoliakov.domain.entity.User;
import by.anpoliakov.domain.enums.ActionType;
import by.anpoliakov.exception.AuditLogException;
import by.anpoliakov.repository.AuditLogRepository;
import by.anpoliakov.service.AuditLogService;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {
    private AuditLogRepository auditLogRepository;

    @Override
    public void addAuditLog(User user, ActionType actionType) {
        AuditLog auditLog = new AuditLog(user.getLogin(), LocalDateTime.now(), actionType);
        auditLogRepository.add(auditLog);
    }

    @Override
    public List<AuditLog> getAuditLogsByLogin(String loginUser) throws AuditLogException {
        Optional<List<AuditLog>> optionalAuditLogList = auditLogRepository.getAuditLogsByLogin(loginUser);
        if(optionalAuditLogList.isEmpty()){
            throw new AuditLogException("No actions from the user - " + loginUser);
        }

        return optionalAuditLogList.get();
    }
}
