package org.example;

import Utils.LectorCSV;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import Modelos.Pacientes;

public class Main {
    public static void main(String[] args) {
        List<Pacientes> pacientes = LectorCSV.leerTratamiento("tratamientos.csv");

        for(var paciente: pacientes) {
            System.out.println(paciente);
        }
    }
}