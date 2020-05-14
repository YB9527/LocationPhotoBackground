package com.xupu.common.service;

import com.xupu.common.po.Task;

import java.util.List;

public interface ITaskService {
      void SaveTasks(List<Task> tasks);

    void save(Task task);
}
