package com.bmc.files_loader.services.print_manager;

import org.springframework.stereotype.Service;

@Service
public class PrintManagerImpl implements PrintManager{
    @Override
    public void printString(StringBuilder str) {
        System.out.println(str);
    }
}
