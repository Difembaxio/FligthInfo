package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import org.apache.commons.math3.stat.descriptive.rank.Median;
import java.util.*;

public class Statistic {

    public static void main(String[] args) throws IOException {
        Statistic statistic = new Statistic();
        TicketList ticketList = statistic.readTicket();
        String origin = "VVO";
        String destination = "TLV";
        System.out.println("Минимальное время полета между городами Владивосток и Тель-Авив для каждого авиаперевозчика :");
        Map<String, Duration> minFlightTimes = statistic.calculateMinFlightTimes(origin, destination, ticketList.getTickets());
        for (Map.Entry<String, Duration> entry : minFlightTimes.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().toString().substring(2));
        }
        System.out.println("Разница между средней ценой  и медианой для полета между городами  Владивосток и Тель-Авив :");
        System.out.println(statistic.differenceBetweenAvgAndMedian(origin,destination,ticketList.getTickets()));
    }

    public TicketList readTicket() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("tickets.json");
        return objectMapper.readValue(file, TicketList.class);
    }

    public Map<String, Duration> calculateMinFlightTimes(String origin, String destination, List<Ticket> ticketList) {
        Map<String, Duration> minDurations = new HashMap<>();
        for (Ticket ticket : ticketList) {
            if (ticket.getOrigin().equals(origin) && ticket.getDestination().equals(destination)) {
                String carrier = ticket.getCarrier();
                Duration duration = Duration.between
                        (LocalDateTime.of(ticket.getDepartureDate(), ticket.getDepartureTime()),
                                LocalDateTime.of(ticket.getArrivalDate(), ticket.getArrivalTime()));
                if (!minDurations.containsKey(carrier)) {
                    minDurations.put(carrier, duration);
                }
            }
        }
        return minDurations;
    }

    public Double differenceBetweenAvgAndMedian(String origin, String destination, List<Ticket> ticketList) {
        List<Double> price = new ArrayList<>();
        for (Ticket ticket : ticketList) {
            if (ticket.getOrigin().equals(origin) && ticket.getDestination().equals(destination)) {
                price.add(ticket.getPrice());
            }
        }
        double sum = 0;
        if (!price.isEmpty()) {
            for (Double mark : price) {
                sum += mark;
            }
        }
        double avg =  sum / price.size();

        Median median = new Median();
        double medianValue = median.evaluate(price.stream()
                .mapToDouble(Double::doubleValue)
                .toArray());
        return avg - medianValue;
    }
}
