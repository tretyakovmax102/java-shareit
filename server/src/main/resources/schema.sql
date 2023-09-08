CREATE TABLE IF NOT EXISTS PUBLIC.USERS
(
    user_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (user_id),
    CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS PUBLIC.REQUESTS
(
    request_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    user_id BIGINT NOT NULL,
    description VARCHAR(200) NOT NULL,
    created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_request PRIMARY KEY (request_id),
    CONSTRAINT userInRequest_FK FOREIGN KEY (user_id) REFERENCES Public.Users(user_id)
);

CREATE TABLE IF NOT EXISTS PUBLIC.ITEMS
(
    item_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    request_id BIGINT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(200) NOT NULL,
    available BOOLEAN NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_item PRIMARY KEY (item_id),
    CONSTRAINT ownerInBooking_FK FOREIGN KEY (user_id) REFERENCES PUBLIC.users(user_id),
    CONSTRAINT request_FK FOREIGN KEY (request_id) REFERENCES Public.REQUESTS(request_id)
);

CREATE TABLE IF NOT EXISTS PUBLIC.BOOKINGS
(
    booking_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    booking_start TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    booking_end TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    item_id BIGINT NOT NULL,
    booker_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT pk_booking PRIMARY KEY (booking_id),
    CONSTRAINT itemInBooking_FK FOREIGN KEY (item_id) REFERENCES Public.ITEMS(item_id),
    CONSTRAINT bookerInBooking_FK FOREIGN KEY (booker_id) REFERENCES Public.Users(user_id)
);

CREATE TABLE IF NOT EXISTS PUBLIC.COMMENTS
(
    comment_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    text VARCHAR(400) NOT NULL,
    item_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_comment PRIMARY KEY (comment_id),
    CONSTRAINT itemInComment_FK FOREIGN KEY (item_id) REFERENCES PUBLIC.items(item_id),
    CONSTRAINT userInComment_FK FOREIGN KEY (user_id) REFERENCES PUBLIC.users(user_id)
);