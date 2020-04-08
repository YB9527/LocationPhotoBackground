package com.xupu.cbd.service;

import com.xupu.cbd.dao.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public class PhotoService implements   IPhotoService {

    PhotoRepository photoRepository;
}
