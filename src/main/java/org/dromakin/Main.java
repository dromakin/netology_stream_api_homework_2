package org.dromakin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        Stream<Person> streamTask1 = persons.stream();

        long counted = streamTask1
                .filter(x -> (x.getAge() < 18))
                .count();

        logger.info("Task 1: Количество несовершеннолетних (т.е. людей младше 18 лет): {}", counted);

        Stream<Person> streamTask2 = persons.stream();

        // Получить список фамилий призывников (т.е. мужчин от 18 и до 27 лет).

        List<String> filteredDataTask2 = streamTask2
                .filter(x -> (x.getAge() < 27 && x.getAge() > 18))
                .map(Person::getFamily)
                .collect(Collectors.toList());

        logger.info("Task 2: список фамилий призывников (т.е. мужчин от 18 и до 27 лет) {}", filteredDataTask2.size());


        Stream<Person> streamTask3 = persons.stream();

        List<Person> filteredDataTask3 = streamTask3
                .filter(x -> (x.getAge() > 18 && ((x.getSex() == Sex.MAN && x.getAge() < 65) || (x.getSex() == Sex.WOMAN && x.getAge() < 60))))
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());

        logger.info("Task 3: Получить отсортированный по фамилии список потенциально работоспособных людей с высшим образованием в выборке (т.е. людей с высшим образованием от 18 до 60 лет для женщин и до 65 лет для мужчин) {}", filteredDataTask3.size());

    }
}