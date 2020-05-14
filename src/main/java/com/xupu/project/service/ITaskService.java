package com.xupu.project.service;

import com.xupu.project.po.Task;

import java.util.List;

public interface ITaskService {
      void SaveTasks(List<Task> tasks);

    void save(Task task);
}
