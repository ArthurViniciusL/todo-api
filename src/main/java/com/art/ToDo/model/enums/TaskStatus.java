package com.art.ToDo.model.enums;

import lombok.Getter;

@Getter
public enum TaskStatus {
    NOT_STATED("not started"),
    IN_PROGRESS("in progress"),
    COMPLETE("complete");

    private final String TASK_STATUS;

    TaskStatus(String taskStatus) {
        this.TASK_STATUS = taskStatus;
    }
}
