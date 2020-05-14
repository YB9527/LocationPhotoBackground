package com.xupu.common.service;

import com.xupu.common.dao.TaskRepository;
import com.xupu.common.po.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService implements ITaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void SaveTasks(List<Task> tasks) {
        taskRepository.saveAll(tasks);
    }

    @Override
    public void save(Task task) {
        taskRepository.save(task);
    }
}
