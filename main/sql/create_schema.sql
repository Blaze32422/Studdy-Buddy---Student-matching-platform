CREATE TABLE Users (
	user_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    email VARCHAR(100) UNIQUE NOT NULL,
    major VARCHAR(50),
    school_year INT
);

CREATE TABLE Posts (
	post_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    description VARCHAR(300) NOT NULL,
    date_posted DATE,
    class VARCHAR(100),
    status BOOLEAN,
    user_id INT NOT NULL,
    accepter_id INT,
    FOREIGN KEY (accepter_id) REFERENCES Users(user_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

CREATE TABLE Acceptances (
    date_accepted DATE,
    user_id INT NOT NULL,
    post_id INT NOT NULL,
    PRIMARY KEY (user_id, post_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (post_id) REFERENCES Posts(post_id)
);



CREATE TABLE Messages (
	message VARCHAR(300),
    time_sent TIMESTAMP,
    sender_id INT NOT NULL,
    receiver_id INT NOT NULL,
    FOREIGN KEY (sender_id) REFERENCES Users(user_id),
    FOREIGN KEY (receiver_id) REFERENCES Users(user_id),
    PRIMARY KEY (sender_id, receiver_id, time_sent)
);

