package org.example.domain.seat;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.SeatsDto;
import org.example.entity.BaseEntity;
import org.example.exception.SeatException;

import java.util.Comparator;
import java.util.List;

import static org.example.baseresponse.BaseResponseStatus.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seat extends BaseEntity {
    private static final int MIN_SEAT_COUNT = 1;
    private static final int MAX_SEAT_COUNT = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id", nullable = false)
    private Long id;

    @Column(nullable = false, name = "seat_row")
    @Enumerated(EnumType.STRING)
    private Row row;

    @Column(nullable = false, name = "seat_col")
    @Enumerated(EnumType.STRING)
    private Col col;

    @Column(nullable = false)
    private Long screenRoomId;

    @Builder
    public Seat(Long id, Row row, Col col, Long screenRoomId) {
        this.id = id;
        this.row = row;
        this.col = col;
        this.screenRoomId = screenRoomId;
    }

    public static Seat of(Long id, Row row, Col col, Long screenRoomId) {
        return Seat.builder()
                .id(id)
                .row(row)
                .col(col)
                .screenRoomId(screenRoomId)
                .build();
    }

    public static void validateSeatCount(int seatSize) {
        if (seatSize > MAX_SEAT_COUNT || seatSize < MIN_SEAT_COUNT) {
            throw new SeatException(UNAVAILABLE_SEAT_COUNT_ERROR);
        }
    }

    public static void validateContinuousSeats(List<SeatsDto> seats) {
        seats.sort(Comparator.comparing(seat -> seat.getCol().getColumn()));

        for (int i = 1; i < seats.size(); i++) {
            SeatsDto prev = seats.get(i - 1);
            SeatsDto current = seats.get(i);

            if (!prev.getRow().getRow().equals(current.getRow().getRow())) {
                throw new SeatException(SEAT_ROW_DISCONTINUITY_ERROR);
            }

            int prevCol = prev.getCol().getColumn();
            int currentCol = current.getCol().getColumn();
            if (currentCol != prevCol + 1) {
                throw new SeatException(SEAT_COLUMN_DISCONTINUITY_ERROR);
            }
        }
    }
}
