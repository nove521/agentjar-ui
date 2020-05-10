package com.cx.server.service;

import org.junit.Test;

public class ProjectInfoServiceTest {

    @Test
    public void getAllClass() {
    }

    @Test
    public void testClass() {
        Class<ProjectInfoServiceTest> aClass = ProjectInfoServiceTest.class;
        System.out.println(aClass.getName());
        System.out.println(aClass.getSimpleName());
        System.out.println(aClass.getClassLoader());
        System.out.println(aClass.getProtectionDomain().getCodeSource().getLocation());
        System.out.println();

        System.out.println(aClass.getClassLoader().getResource(""));
    }
}