LOCK TABLES `manager` WRITE;

INSERT INTO `manager` VALUES
(1, 8, 'Linda Thompson', 'linda@example.com', 'Human Resources', '555-1212'),
(2, 9, 'Michael Johnson', 'michael@example.com', 'Engineering', '555-3434'),
(3, 2, 'Edward Smith', 'edsmith@example.com', 'Sales', '555-5656');

UNLOCK TABLES;
