LOCK TABLES `job` WRITE;

INSERT INTO `job`  VALUES
(1, 1, 'Human Resources', 'HR Manager Position', '2024-08-01 09:00:00', '2024-09-15 17:00:00', 'HR Manager', 'Responsible for managing HR processes and overseeing recruitment.', 'Experience in HR management required. Certification in HR is a plus.', 'Open'),
(2, 2, 'Software Development', 'Senior Software Engineer Role', '2024-08-10 10:00:00', '2024-09-10 17:00:00', 'Senior Software Engineer', 'Lead the development of software solutions and mentor junior developers.', 'Proficiency in Python and Java required. Leadership experience is a plus.', 'Open'),
(3, 3, 'Sales and Marketing', 'Sales and Marketing Executive', '2024-08-15 11:00:00', '2024-09-15 17:00:00', 'Sales and Marketing Executive', 'Develop and implement marketing strategies, manage client relationships.', 'Experience in digital marketing and client management required.', 'Open'), 
(4, 2, 'Engineering', 'DevOps Engineer Position', '2024-08-05 09:30:00', '2024-09-20 17:00:00', 'DevOps Engineer', 'Implement CI/CD pipelines and manage infrastructure.', 'Experience with Jenkins, Docker, and Kubernetes required.', 'Closed'),
(5, 1, 'Human Resources', 'Recruitment Specialist Role', '2024-08-20 12:00:00', '2024-09-20 17:00:00', 'Recruitment Specialist', 'Handle end-to-end recruitment processes and onboarding.', 'Strong communication skills and experience in recruitment required.', 'Open');

UNLOCK TABLES;
