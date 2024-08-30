LOCK TABLES `candidate` WRITE;

INSERT INTO `candidate`  VALUES
(1, 1, 'Cindy Loo', 'cindyloo@example.com', '123 Main St, Springfield', '555-1234', 'Experienced software developer with a background in web development.'),
(2, 6, 'Jackson Avery', 'jacksonavery@example.com', '456 Elm St, Rivertown', '555-5678', 'Project manager with 5 years of experience in the IT industry.'),
(3, 4, 'John Doe', 'johndoe@example.com', '789 Oak St, Lakeside', '555-8765', 'Junior data analyst with proficiency in Python and SQL.'),
(4, 5, 'Jane Smith', 'janesmith@example.com', '101 Pine St, Maple City', '555-4321', 'Marketing specialist with a focus on digital campaigns and SEO.'),
(5, 7, 'Emily Clark', 'emilyclark@example.com', '202 Birch St, Cedar Town', '555-6543', 'Graphic designer with a passion for creating visually appealing content.');

UNLOCK TABLES;
