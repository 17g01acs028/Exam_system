

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

--
-- Database: `online_exam_system`
--

-- --------------------------------------------------------

--
-- Table structure for table `auth_table`
--

CREATE TABLE `auth_table` (
  `user_id` bigint(20) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `token_created_at` datetime DEFAULT NULL,
  `token_expires_at` datetime DEFAULT NULL,
  `login_attempts` int(11) NOT NULL DEFAULT 0,
  `reset_key` varchar(255) DEFAULT NULL,
  `reset_key_expire_time` datetime DEFAULT NULL,
  `role` varchar(10) DEFAULT NULL,
  `registration_status` int(11) NOT NULL DEFAULT 0,
  `date_created` datetime NOT NULL DEFAULT current_timestamp(),
  `date_modified` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `auth_table`
--

INSERT INTO `auth_table` (`user_id`, `username`, `password`, `token`, `token_created_at`, `token_expires_at`, `login_attempts`, `reset_key`, `reset_key_expire_time`, `role`, `registration_status`, `date_created`, `date_modified`) VALUES
(1, 'john.doe', 'QxAEsnErYTzSycUW5ckwlQ==', 'HG0tvt86SAC2j242lAB6T7pmCj4n2sm6m9+aXZEw9C2qx8GHHcn5zmqCRDSr4l2pT3fN1wTpcysrP/fAhu7+L6dcA8rRz8CqV7rLHHshG3NtQ26SKj98WdjEN65CFDcX', '2024-03-09 00:22:01', '2024-03-09 00:27:01', 0, NULL, NULL, 'teacher', 1, '2024-03-08 13:56:16', '2024-03-09 00:22:01'),
(2, 'jane.doe', 'QxAEsnErYTzSycUW5ckwlQ==', 'OKTSb+lt0C6rDFtyW8EzWy639brry3MresOVlPeTZZsprsSmHVLKl+LRRBUspUPeoKwJl8EtImCoLQ6/w9LA4qAuYKNbtg0s9eugiMkl5Q8=', '2024-03-09 00:24:04', '2024-03-09 00:29:04', 0, NULL, NULL, 'admin', 1, '2024-03-08 13:56:16', '2024-03-09 00:24:04'),
(3, 'jim.doe', 'QxAEsnErYTzSycUW5ckwlQ==', NULL, NULL, NULL, 0, NULL, NULL, 'admin', 1, '2024-03-08 13:57:07', NULL),
(4, 'jill.beam', 'QxAEsnErYTzSycUW5ckwlQ==', NULL, NULL, NULL, 0, NULL, NULL, 'teacher', 1, '2024-03-08 13:57:07', NULL),
(5, 'jake.long', 'QxAEsnErYTzSycUW5ckwlQ==', NULL, NULL, NULL, 0, NULL, NULL, 'teacher', 1, '2024-03-08 13:57:28', NULL),
(6, 'student1', 'QxAEsnErYTzSycUW5ckwlQ==', NULL, NULL, NULL, 0, NULL, NULL, 'student', 1, '2024-03-08 14:05:44', NULL),
(7, 'student2', 'QxAEsnErYTzSycUW5ckwlQ==', NULL, NULL, NULL, 0, NULL, NULL, 'student', 1, '2024-03-08 14:05:44', NULL),
(8, 'student3', 'QxAEsnErYTzSycUW5ckwlQ==', '8htA4HiqpA3gvxeQkoLgOTMaz6TqSF+8Lv1pjVD50dq6ElHEfDKz+IktIAx4Y46uiGH0EM1Cei1yhIZweb/JhC5+xmEkazLlOy0qiKWZKao=', '2024-03-09 00:28:10', '2024-03-09 00:33:10', 0, NULL, NULL, 'student', 1, '2024-03-08 14:06:22', '2024-03-09 00:28:10'),
(9, 'student4', 'QxAEsnErYTzSycUW5ckwlQ==', NULL, NULL, NULL, 0, NULL, NULL, 'student', 1, '2024-03-08 14:06:22', NULL),
(10, 'student5', 'QxAEsnErYTzSycUW5ckwlQ==', NULL, NULL, NULL, 0, NULL, NULL, 'student', 1, '2024-03-08 14:06:22', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `class`
--

CREATE TABLE `class` (
  `class_id` bigint(20) NOT NULL,
  `class_name` varchar(50) NOT NULL,
  `date_created` datetime NOT NULL DEFAULT current_timestamp(),
  `date_modified` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `class`
--

INSERT INTO `class` (`class_id`, `class_name`, `date_created`, `date_modified`) VALUES
(1, 'Class 1', '2024-02-01 05:43:47', NULL),
(2, 'Class 2', '2024-02-01 05:43:47', NULL),
(3, 'Class 3', '2024-02-01 05:43:47', NULL),
(4, 'Class 4', '2024-02-01 05:43:47', NULL),
(5, 'class 4 new', '2024-02-19 19:19:03', NULL),
(6, 'Form 4 west East', '2024-02-26 19:56:10', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `exam`
--

CREATE TABLE `exam` (
  `exam_id` bigint(20) NOT NULL,
  `exam_name` varchar(50) NOT NULL,
  `date_created` datetime NOT NULL DEFAULT current_timestamp(),
  `date_modified` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `exam`
--

INSERT INTO `exam` (`exam_id`, `exam_name`, `date_created`, `date_modified`) VALUES
(1, 'Final exam', '2024-02-01 05:42:04', NULL),
(2, 'Midterm Exam', '2024-02-01 05:42:04', NULL),
(3, 'End of year Exam', '2024-02-01 05:42:04', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `exam_schedule`
--

CREATE TABLE `exam_schedule` (
  `exam_schedule_id` bigint(20) NOT NULL,
  `exam_id` bigint(20) NOT NULL,
  `subject_id` bigint(20) NOT NULL,
  `exam_date` datetime DEFAULT NULL,
  `exam_duration` int(11) DEFAULT NULL,
  `teacher_id` bigint(20) NOT NULL,
  `class_id` bigint(20) NOT NULL,
  `month_year` varchar(7) DEFAULT NULL,
  `date_created` datetime NOT NULL DEFAULT current_timestamp(),
  `date_modified` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `exam_schedule`
--

INSERT INTO `exam_schedule` (`exam_schedule_id`, `exam_id`, `subject_id`, `exam_date`, `exam_duration`, `teacher_id`, `class_id`, `month_year`, `date_created`, `date_modified`) VALUES
(1, 1, 1, '2024-02-20 09:14:07', 30, 21, 1, '2024-03', '2024-02-01 06:19:18', NULL),
(2, 1, 2, '2024-02-18 09:16:03', 60, 22, 1, '2024-03', '2024-02-01 06:19:18', NULL),
(3, 1, 3, '2024-02-19 09:16:45', 40, 23, 1, '2024-03', '2024-02-01 06:19:18', NULL),
(4, 1, 4, '2024-02-18 09:17:30', 40, 24, 1, '2024-03', '2024-02-01 06:19:18', NULL),
(5, 1, 5, '2024-02-19 09:18:28', 40, 25, 1, '2024-03', '2024-02-01 06:19:18', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `options`
--

CREATE TABLE `options` (
  `option_id` bigint(20) NOT NULL,
  `question_id` bigint(20) NOT NULL,
  `option_label` varchar(2) NOT NULL,
  `option_value` text NOT NULL,
  `correct` int(11) NOT NULL DEFAULT 0,
  `date_created` datetime NOT NULL DEFAULT current_timestamp(),
  `date_modified` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `options`
--

INSERT INTO `options` (`option_id`, `question_id`, `option_label`, `option_value`, `correct`, `date_created`, `date_modified`) VALUES
(1, 1, 'A', 'A figure of speech', 0, '2024-02-01 06:36:53', NULL),
(2, 1, 'B', 'A literal expression', 0, '2024-02-01 06:36:53', NULL),
(3, 1, 'C', 'A comparison without using like or as', 1, '2024-02-01 06:36:53', NULL),
(4, 1, 'D', 'A type of onomatopoeia', 0, '2024-02-01 06:36:53', NULL),
(5, 2, 'A', 'A comedy', 0, '2024-02-01 06:36:53', NULL),
(6, 2, 'B', 'A tragedy', 1, '2024-02-01 06:36:53', NULL),
(7, 2, 'C', 'A history play', 0, '2024-02-01 06:36:53', NULL),
(8, 2, 'D', 'A romance', 0, '2024-02-01 06:36:53', NULL),
(9, 3, 'A', 'Its educational impact', 0, '2024-02-01 06:36:53', NULL),
(10, 3, 'B', 'Its influence on technology', 0, '2024-02-01 06:36:53', NULL),
(11, 3, 'C', 'Its entertainment value', 0, '2024-02-01 06:36:53', NULL),
(12, 3, 'D', 'Its cultural and societal influence', 1, '2024-02-01 06:36:53', NULL),
(13, 4, 'A', 'They are the same', 0, '2024-02-01 06:36:53', NULL),
(14, 4, 'B', 'A simile uses like or as', 1, '2024-02-01 06:36:53', NULL),
(15, 4, 'C', 'A metaphor is longer', 0, '2024-02-01 06:36:53', NULL),
(16, 4, 'D', 'A simile is more direct', 0, '2024-02-01 06:36:53', NULL),
(17, 5, 'A', 'Predicate and object', 0, '2024-02-01 06:36:53', NULL),
(18, 5, 'B', 'Subject and verb', 1, '2024-02-01 06:36:53', NULL),
(19, 5, 'C', 'Noun and pronoun', 0, '2024-02-01 06:36:53', NULL),
(20, 5, 'D', 'Adjective and adverb', 0, '2024-02-01 06:36:53', NULL),
(21, 6, 'A', 'x = 3', 1, '2024-02-01 06:38:40', NULL),
(22, 6, 'B', 'x = 4', 0, '2024-02-01 06:38:40', NULL),
(23, 6, 'C', 'x = 5', 0, '2024-02-01 06:38:40', NULL),
(24, 6, 'D', 'x = 6', 0, '2024-02-01 06:38:40', NULL),
(25, 7, 'A', '2x + 3', 1, '2024-02-01 06:38:40', NULL),
(26, 7, 'B', '2x', 0, '2024-02-01 06:38:40', NULL),
(27, 7, 'C', 'x^2 + 3x', 0, '2024-02-01 06:38:40', NULL),
(28, 7, 'D', 'x^2', 0, '2024-02-01 06:38:40', NULL),
(29, 8, 'A', '0.7', 1, '2024-02-01 06:38:40', NULL),
(30, 8, 'B', '0.3', 0, '2024-02-01 06:38:40', NULL),
(31, 8, 'C', '0.5', 0, '2024-02-01 06:38:40', NULL),
(32, 8, 'D', '0.2', 0, '2024-02-01 06:38:40', NULL),
(33, 9, 'A', 'A straight line', 0, '2024-02-01 06:38:40', NULL),
(34, 9, 'B', 'A parabola', 0, '2024-02-01 06:38:40', NULL),
(35, 9, 'C', 'A hyperbola', 0, '2024-02-01 06:38:40', NULL),
(36, 9, 'D', 'A diagonal line', 1, '2024-02-01 06:38:40', NULL),
(37, 10, 'A', '25π square units', 0, '2024-02-01 06:38:40', NULL),
(38, 10, 'B', '50π square units', 0, '2024-02-01 06:38:40', NULL),
(39, 10, 'C', '75π square units', 1, '2024-02-01 06:38:40', NULL),
(40, 10, 'D', '100π square units', 0, '2024-02-01 06:38:40', NULL),
(41, 11, 'A', 'Hadithi', 0, '2024-02-01 06:40:45', NULL),
(42, 11, 'B', 'Methali', 0, '2024-02-01 06:40:45', NULL),
(43, 11, 'C', 'Fasihi ya kusisimua', 1, '2024-02-01 06:40:45', NULL),
(44, 11, 'D', 'Shairi', 0, '2024-02-01 06:40:45', NULL),
(45, 12, 'A', 'Ukakamavu', 0, '2024-02-01 06:40:45', NULL),
(46, 12, 'B', 'Uhalisia', 1, '2024-02-01 06:40:45', NULL),
(47, 12, 'C', 'Ushujaa', 0, '2024-02-01 06:40:45', NULL),
(48, 12, 'D', 'Mapenzi', 0, '2024-02-01 06:40:45', NULL),
(49, 13, 'A', 'Kuwa na haraka huleta baraka', 0, '2024-02-01 06:40:45', NULL),
(50, 13, 'B', 'Kuwa na haraka huleta matatizo', 1, '2024-02-01 06:40:45', NULL),
(51, 13, 'C', 'Kuwa na haraka huleta mafanikio', 0, '2024-02-01 06:40:45', NULL),
(52, 13, 'D', 'Kuwa na haraka huleta furaha', 0, '2024-02-01 06:40:45', NULL),
(53, 14, 'A', 'Upendo ni kama ua', 1, '2024-02-01 06:40:45', NULL),
(54, 14, 'B', 'Upendo ni kama jua', 0, '2024-02-01 06:40:45', NULL),
(55, 14, 'C', 'Upendo ni kama mwezi', 0, '2024-02-01 06:40:45', NULL),
(56, 14, 'D', 'Upendo ni kama nyota', 0, '2024-02-01 06:40:45', NULL),
(57, 15, 'A', 'NG-VA, LI-YA', 1, '2024-02-01 06:40:45', NULL),
(58, 15, 'B', 'U-WA, PA-MU', 0, '2024-02-01 06:40:45', NULL),
(59, 15, 'C', 'KI-VI, JI-MA', 0, '2024-02-01 06:40:45', NULL),
(60, 15, 'D', 'KU-TU, A-WA', 0, '2024-02-01 06:40:45', NULL),
(61, 16, 'A', 'Economic inequality', 0, '2024-02-01 06:43:23', NULL),
(62, 16, 'B', 'Political alliances', 0, '2024-02-01 06:43:23', NULL),
(63, 16, 'C', 'Territorial disputes', 1, '2024-02-01 06:43:23', NULL),
(64, 16, 'D', 'Religious differences', 0, '2024-02-01 06:43:23', NULL),
(65, 17, 'A', 'Evaporation and precipitation', 1, '2024-02-01 06:43:23', NULL),
(66, 17, 'B', 'Condensation and transpiration', 0, '2024-02-01 06:43:23', NULL),
(67, 17, 'C', 'Photosynthesis and respiration', 0, '2024-02-01 06:43:23', NULL),
(68, 17, 'D', 'Erosion and deposition', 0, '2024-02-01 06:43:23', NULL),
(69, 18, 'A', 'Improved economies', 0, '2024-02-01 06:43:23', NULL),
(70, 18, 'B', 'Cultural exchange', 0, '2024-02-01 06:43:23', NULL),
(71, 18, 'C', 'Exploitation of resources', 1, '2024-02-01 06:43:23', NULL),
(72, 18, 'D', 'Technological advancements', 0, '2024-02-01 06:43:23', NULL),
(73, 19, 'A', 'Executive, Legislative, and Judicial', 1, '2024-02-01 06:43:23', NULL),
(74, 19, 'B', 'Educational, Medical, and Political', 0, '2024-02-01 06:43:23', NULL),
(75, 19, 'C', 'Economic, Social, and Cultural', 0, '2024-02-01 06:43:23', NULL),
(76, 19, 'D', 'Urban, Rural, and Suburban', 0, '2024-02-01 06:43:23', NULL),
(77, 20, 'A', 'Non-renewable energy sources', 0, '2024-02-01 06:43:23', NULL),
(78, 20, 'B', 'Natural resources that can be replenished', 1, '2024-02-01 06:43:23', NULL),
(79, 20, 'C', 'Resources found only in urban areas', 0, '2024-02-01 06:43:23', NULL),
(80, 20, 'D', 'Artificially produced resources', 0, '2024-02-01 06:43:23', NULL),
(81, 21, 'A', 'Conversion of sunlight into chemical energy', 1, '2024-02-01 08:00:11', NULL),
(82, 21, 'B', 'Conversion of chemical energy into kinetic energy', 0, '2024-02-01 08:00:11', NULL),
(83, 21, 'C', 'Process of water cycling through the environment', 0, '2024-02-01 08:00:11', NULL),
(84, 21, 'D', 'Formation of glucose and oxygen', 0, '2024-02-01 08:00:11', NULL),
(85, 22, 'A', 'For every action, there is an equal reaction', 1, '2024-02-01 08:00:11', NULL),
(86, 22, 'B', 'Objects in motion stay in motion', 0, '2024-02-01 08:00:11', NULL),
(87, 22, 'C', 'Force equals mass times acceleration', 0, '2024-02-01 08:00:11', NULL),
(88, 22, 'D', 'Energy cannot be created or destroyed', 0, '2024-02-01 08:00:11', NULL),
(89, 23, 'A', 'Cell wall, nucleus, chloroplast', 0, '2024-02-01 08:00:11', NULL),
(90, 23, 'B', 'Nucleus, mitochondria, ribosomes', 1, '2024-02-01 08:00:11', NULL),
(91, 23, 'C', 'Cell membrane, cytoplasm, vacuole', 0, '2024-02-01 08:00:11', NULL),
(92, 23, 'D', 'Endoplasmic reticulum, Golgi apparatus, lysosomes', 0, '2024-02-01 08:00:11', NULL),
(93, 24, 'A', 'Solid, liquid, gas, and plasma', 1, '2024-02-01 08:00:11', NULL),
(94, 24, 'B', 'Solid, liquid, gas, and vapor', 0, '2024-02-01 08:00:11', NULL),
(95, 24, 'C', 'Solid, fluid, gas, and plasma', 0, '2024-02-01 08:00:11', NULL),
(96, 24, 'D', 'Solid, liquid, mist, and ether', 0, '2024-02-01 08:00:11', NULL),
(97, 25, 'A', 'A community of living organisms and their physical environment', 1, '2024-02-01 08:00:11', NULL),
(98, 25, 'B', 'A group of similar species living in the same area', 0, '2024-02-01 08:00:11', NULL),
(99, 25, 'C', 'All living organisms on Earth', 0, '2024-02-01 08:00:11', NULL),
(100, 25, 'D', 'A specific geographical area with distinct species', 0, '2024-02-01 08:00:11', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `questions`
--

CREATE TABLE `questions` (
  `question_id` bigint(20) NOT NULL,
  `exam_schedule_id` bigint(20) NOT NULL,
  `text` text NOT NULL,
  `marks` int(11) NOT NULL,
  `date_created` datetime NOT NULL DEFAULT current_timestamp(),
  `date_modified` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `questions`
--

INSERT INTO `questions` (`question_id`, `exam_schedule_id`, `text`, `marks`, `date_created`, `date_modified`) VALUES
(1, 1, 'Define the term \"metaphor\".', 5, '2024-02-01 06:26:22', NULL),
(2, 1, 'Analyze the main theme in Shakespeare’s \"Macbeth\".', 5, '2024-02-01 06:26:22', NULL),
(3, 1, 'Write a short essay on the impact of literature in modern society.', 5, '2024-02-01 06:26:22', NULL),
(4, 1, 'Explain the difference between a simile and a metaphor.', 5, '2024-02-01 06:26:22', NULL),
(5, 1, 'Identify the subject and predicate in this sentence: \"The quick brown fox jumps over the lazy dog.\"', 5, '2024-02-01 06:26:22', NULL),
(6, 2, 'Solve the equation 3x + 5 = 14.', 5, '2024-02-01 06:26:22', NULL),
(7, 2, 'Calculate the derivative of the function f(x) = x^2 + 3x.', 5, '2024-02-01 06:26:22', NULL),
(8, 2, 'If the probability of event A is 0.3, what is the probability of its complement?', 5, '2024-02-01 06:26:22', NULL),
(9, 2, 'Graph the function y = 2x - 4.', 5, '2024-02-01 06:26:22', NULL),
(10, 2, 'Find the area of a circle with radius 5 units.', 5, '2024-02-01 06:26:22', NULL),
(11, 3, 'Taja na ueleze sifa kuu za fasihi simulizi.', 5, '2024-02-01 06:26:22', NULL),
(12, 3, 'Changanua maudhui ya riwaya ya \"Kusadikika\" na Shaaban Robert.', 5, '2024-02-01 06:26:22', NULL),
(13, 3, 'Eleza maana ya methali: \"Haraka haraka haina baraka.\"', 5, '2024-02-01 06:26:22', NULL),
(14, 3, 'Tunga sentensi kwa kutumia neno \"upendo\".', 5, '2024-02-01 06:26:22', NULL),
(15, 3, 'Taja na ueleze ngeli za Kiswahili.', 5, '2024-02-01 06:26:22', NULL),
(16, 4, 'Describe the major causes of World War II.', 5, '2024-02-01 06:26:22', NULL),
(17, 4, 'Explain the water cycle and its importance to the environment.', 5, '2024-02-01 06:26:22', NULL),
(18, 4, 'Discuss the impact of colonialism in African countries.', 5, '2024-02-01 06:26:22', NULL),
(19, 4, 'Name and describe the three branches of government.', 5, '2024-02-01 06:26:22', NULL),
(20, 4, 'What are renewable resources? Give two examples.', 5, '2024-02-01 06:26:22', NULL),
(21, 5, 'What is the process of photosynthesis?', 5, '2024-02-01 06:26:22', NULL),
(22, 5, 'Explain Newton’s third law of motion.', 5, '2024-02-01 06:26:22', NULL),
(23, 5, 'Describe the structure of a plant cell.', 5, '2024-02-01 06:26:22', NULL),
(24, 5, 'What are the states of matter? Give an example of each.', 5, '2024-02-01 06:26:22', NULL),
(25, 5, 'Define and explain the term \"ecosystem\".', 5, '2024-02-01 06:26:22', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `responses`
--

CREATE TABLE `responses` (
  `response_id` bigint(20) NOT NULL,
  `question_id` bigint(20) NOT NULL,
  `student_id` bigint(20) NOT NULL,
  `option_id` bigint(20) NOT NULL,
  `date_created` datetime NOT NULL DEFAULT current_timestamp(),
  `date_modified` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `responses`
--

INSERT INTO `responses` (`response_id`, `question_id`, `student_id`, `option_id`, `date_created`, `date_modified`) VALUES
(26, 1, 1, 3, '2024-02-01 08:07:57', NULL),
(27, 2, 1, 6, '2024-02-01 08:07:57', NULL),
(28, 3, 1, 12, '2024-02-01 08:07:57', NULL),
(29, 4, 1, 14, '2024-02-01 08:07:57', NULL),
(30, 5, 1, 18, '2024-02-01 08:07:57', NULL),
(31, 6, 1, 21, '2024-02-01 08:07:57', NULL),
(32, 7, 1, 25, '2024-02-01 08:07:57', NULL),
(33, 8, 1, 29, '2024-02-01 08:07:57', NULL),
(34, 9, 1, 36, '2024-02-01 08:07:57', NULL),
(35, 10, 1, 39, '2024-02-01 08:07:57', NULL),
(36, 11, 1, 43, '2024-02-01 08:07:57', NULL),
(37, 12, 1, 46, '2024-02-01 08:07:57', NULL),
(38, 13, 1, 50, '2024-02-01 08:07:57', NULL),
(39, 14, 1, 53, '2024-02-01 08:07:57', NULL),
(40, 15, 1, 57, '2024-02-01 08:07:57', NULL),
(41, 16, 1, 63, '2024-02-01 08:07:57', NULL),
(42, 17, 1, 65, '2024-02-01 08:07:57', NULL),
(43, 18, 1, 71, '2024-02-01 08:07:57', NULL),
(44, 19, 1, 73, '2024-02-01 08:07:57', NULL),
(45, 20, 1, 77, '2024-02-01 08:07:57', NULL),
(46, 21, 1, 81, '2024-02-01 08:07:58', NULL),
(47, 22, 1, 85, '2024-02-01 08:07:58', NULL),
(48, 23, 1, 90, '2024-02-01 08:07:58', NULL),
(49, 24, 1, 93, '2024-02-01 08:07:58', NULL),
(50, 25, 1, 97, '2024-02-01 08:07:58', NULL),
(51, 1, 2, 3, '2024-02-01 08:09:35', NULL),
(52, 2, 2, 5, '2024-02-01 08:09:35', NULL),
(53, 3, 2, 12, '2024-02-01 08:09:35', NULL),
(54, 4, 2, 14, '2024-02-01 08:09:35', NULL),
(55, 5, 2, 17, '2024-02-01 08:09:35', NULL),
(56, 6, 2, 22, '2024-02-01 08:09:35', NULL),
(57, 7, 2, 26, '2024-02-01 08:09:35', NULL),
(58, 8, 2, 29, '2024-02-01 08:09:35', NULL),
(59, 9, 2, 33, '2024-02-01 08:09:35', NULL),
(60, 10, 2, 39, '2024-02-01 08:09:35', NULL),
(61, 11, 2, 41, '2024-02-01 08:09:35', NULL),
(62, 12, 2, 46, '2024-02-01 08:09:35', NULL),
(63, 13, 2, 49, '2024-02-01 08:09:35', NULL),
(64, 14, 2, 54, '2024-02-01 08:09:35', NULL),
(65, 15, 2, 57, '2024-02-01 08:09:35', NULL),
(66, 16, 2, 61, '2024-02-01 08:09:35', NULL),
(67, 17, 2, 66, '2024-02-01 08:09:35', NULL),
(68, 18, 2, 71, '2024-02-01 08:09:35', NULL),
(69, 19, 2, 74, '2024-02-01 08:09:35', NULL),
(70, 20, 2, 78, '2024-02-01 08:09:35', NULL),
(71, 21, 2, 81, '2024-02-01 08:09:35', NULL),
(72, 22, 2, 85, '2024-02-01 08:09:35', NULL),
(73, 23, 2, 89, '2024-02-01 08:09:35', NULL),
(74, 24, 2, 94, '2024-02-01 08:09:35', NULL),
(75, 25, 2, 97, '2024-02-01 08:09:35', NULL),
(76, 1, 3, 3, '2024-02-01 09:30:51', NULL),
(77, 2, 3, 6, '2024-02-01 09:30:51', NULL),
(78, 3, 3, 9, '2024-02-01 09:30:51', NULL),
(79, 4, 3, 13, '2024-02-01 09:30:51', NULL),
(80, 5, 3, 18, '2024-02-01 09:30:51', NULL),
(81, 6, 3, 21, '2024-02-01 09:30:52', NULL),
(82, 7, 3, 26, '2024-02-01 09:30:52', NULL),
(83, 8, 3, 30, '2024-02-01 09:30:52', NULL),
(84, 9, 3, 34, '2024-02-01 09:30:52', NULL),
(85, 10, 3, 40, '2024-02-01 09:30:52', NULL),
(86, 11, 3, 44, '2024-02-01 09:30:52', NULL),
(87, 12, 3, 45, '2024-02-01 09:30:52', NULL),
(88, 13, 3, 51, '2024-02-01 09:30:52', NULL),
(89, 14, 3, 53, '2024-02-01 09:30:52', NULL),
(90, 15, 3, 59, '2024-02-01 09:30:52', NULL),
(91, 16, 3, 62, '2024-02-01 09:30:52', NULL),
(92, 17, 3, 67, '2024-02-01 09:30:52', NULL),
(93, 18, 3, 69, '2024-02-01 09:30:52', NULL),
(94, 19, 3, 73, '2024-02-01 09:30:52', NULL),
(95, 20, 3, 79, '2024-02-01 09:30:52', NULL),
(96, 21, 3, 82, '2024-02-01 09:30:52', NULL),
(97, 22, 3, 86, '2024-02-01 09:30:52', NULL),
(98, 23, 3, 91, '2024-02-01 09:30:52', NULL),
(99, 24, 3, 93, '2024-02-01 09:30:52', NULL),
(100, 25, 3, 100, '2024-02-01 09:30:52', NULL),
(101, 1, 4, 4, '2024-02-01 09:42:12', NULL),
(102, 2, 4, 5, '2024-02-01 09:42:12', NULL),
(103, 3, 4, 9, '2024-02-01 09:42:12', NULL),
(104, 4, 4, 14, '2024-02-01 09:42:12', NULL),
(105, 5, 4, 20, '2024-02-01 09:42:12', NULL),
(106, 6, 4, 22, '2024-02-01 09:42:13', NULL),
(107, 7, 4, 25, '2024-02-01 09:42:13', NULL),
(108, 8, 4, 30, '2024-02-01 09:42:13', NULL),
(109, 9, 4, 34, '2024-02-01 09:42:13', NULL),
(110, 10, 4, 40, '2024-02-01 09:42:13', NULL),
(111, 11, 4, 41, '2024-02-01 09:42:13', NULL),
(112, 12, 4, 48, '2024-02-01 09:42:13', NULL),
(113, 13, 4, 50, '2024-02-01 09:42:13', NULL),
(114, 14, 4, 56, '2024-02-01 09:42:13', NULL),
(115, 15, 4, 58, '2024-02-01 09:42:13', NULL),
(116, 16, 4, 64, '2024-02-01 09:42:13', NULL),
(117, 17, 4, 66, '2024-02-01 09:42:13', NULL),
(118, 18, 4, 72, '2024-02-01 09:42:13', NULL),
(119, 19, 4, 74, '2024-02-01 09:42:13', NULL),
(120, 20, 4, 80, '2024-02-01 09:42:13', NULL),
(121, 21, 4, 83, '2024-02-01 09:42:13', NULL),
(122, 22, 4, 87, '2024-02-01 09:42:13', NULL),
(123, 23, 4, 90, '2024-02-01 09:42:13', NULL),
(124, 24, 4, 95, '2024-02-01 09:42:13', NULL),
(125, 25, 4, 99, '2024-02-01 09:42:13', NULL),
(126, 1, 5, 3, '2024-02-01 00:00:00', NULL),
(127, 2, 5, 6, '2024-02-01 00:00:00', NULL),
(128, 3, 5, 12, '2024-02-01 00:00:00', NULL),
(129, 4, 5, 14, '2024-02-01 00:00:00', NULL),
(130, 5, 5, 18, '2024-02-01 00:00:00', NULL),
(131, 6, 5, 21, '2024-02-01 00:00:00', NULL),
(132, 7, 5, 25, '2024-02-01 00:00:00', NULL),
(133, 8, 5, 29, '2024-02-01 00:00:00', NULL),
(134, 9, 5, 36, '2024-02-01 00:00:00', NULL),
(135, 10, 5, 39, '2024-02-01 00:00:00', NULL),
(136, 11, 5, 43, '2024-02-01 00:00:00', NULL),
(137, 12, 5, 46, '2024-02-01 00:00:00', NULL),
(138, 13, 5, 50, '2024-02-01 00:00:00', NULL),
(139, 14, 5, 53, '2024-02-01 00:00:00', NULL),
(140, 15, 5, 57, '2024-02-01 00:00:00', NULL),
(141, 16, 5, 63, '2024-02-01 00:00:00', NULL),
(142, 17, 5, 65, '2024-02-01 00:00:00', NULL),
(143, 18, 5, 71, '2024-02-01 00:00:00', NULL),
(144, 19, 5, 73, '2024-02-01 00:00:00', NULL),
(145, 20, 5, 78, '2024-02-01 00:00:00', NULL),
(146, 21, 5, 81, '2024-02-01 00:00:00', NULL),
(147, 22, 5, 85, '2024-02-01 00:00:00', NULL),
(148, 23, 5, 90, '2024-02-01 00:00:00', NULL),
(149, 24, 5, 93, '2024-02-01 00:00:00', NULL),
(150, 25, 5, 97, '2024-02-01 00:00:00', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `student_id` bigint(20) NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `gender` varchar(25) NOT NULL,
  `date_of_birth` date DEFAULT NULL,
  `guardian_name` varchar(50) NOT NULL,
  `guardian_phone` varchar(25) NOT NULL,
  `class_id` bigint(20) NOT NULL,
  `home_address` varchar(50) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `postal_address` int(11) DEFAULT NULL,
  `student_reg_number` varchar(50) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `date_created` datetime NOT NULL DEFAULT current_timestamp(),
  `date_modified` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`student_id`, `firstname`, `lastname`, `gender`, `date_of_birth`, `guardian_name`, `guardian_phone`, `class_id`, `home_address`, `city`, `postal_address`, `student_reg_number`, `user_id`, `date_created`, `date_modified`) VALUES
(1, 'John', 'Doe1', 'M', '2005-01-01', 'Jane Doe', '0123456789', 1, '123 Main St', 'City1', 90115, '10001', 6, '2024-02-01 06:12:54', NULL),
(2, 'Jane', 'Doe2', 'F', '2005-02-02', 'John Doe', '9876543210', 1, '124 Main St', 'City1', 100200, '10002', 7, '2024-02-01 06:12:54', NULL),
(3, 'Jim', 'Beam1', 'M', '2005-03-03', 'Janet Doe', '0123456781', 1, '125 Main St', 'City1', 140020, '10003', 8, '2024-02-01 06:12:54', NULL),
(4, 'Jill', 'Beam2', 'F', '2005-04-04', 'James Doe', '9876543211', 1, '126 Main St', 'City1', 130012, '10004', 9, '2024-02-01 06:12:54', NULL),
(5, 'Jake', 'Long1', 'M', '2005-05-05', 'Jasmine Doe', '0123456782', 1, '127 Main St', 'City1', 200200, '10005', 10, '2024-02-01 06:12:54', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `subject`
--

CREATE TABLE `subject` (
  `subject_id` bigint(20) NOT NULL,
  `subject_name` varchar(50) NOT NULL,
  `date_created` datetime NOT NULL DEFAULT current_timestamp(),
  `date_modified` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `subject`
--

INSERT INTO `subject` (`subject_id`, `subject_name`, `date_created`, `date_modified`) VALUES
(1, 'English', '2024-02-01 05:45:02', NULL),
(2, 'Mathematics', '2024-02-01 05:45:02', NULL),
(3, 'Kiswahili', '2024-02-01 05:45:02', NULL),
(4, 'Social Studies', '2024-02-01 05:45:02', NULL),
(5, 'Science', '2024-02-01 05:45:02', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `teacher`
--

CREATE TABLE `teacher` (
  `tsc_number` varchar(25) NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `id_number` int(11) NOT NULL,
  `phone` varchar(25) NOT NULL,
  `email` varchar(50) NOT NULL,
  `title` varchar(25) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `postal_code` int(11) DEFAULT NULL,
  `national_insurance_number` int(11) NOT NULL,
  `date_of_birth` date DEFAULT NULL,
  `teacher_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `date_created` datetime NOT NULL DEFAULT current_timestamp(),
  `date_modified` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `teacher`
--

INSERT INTO `teacher` (`tsc_number`, `firstname`, `lastname`, `id_number`, `phone`, `email`, `title`, `address`, `city`, `postal_code`, `national_insurance_number`, `date_of_birth`, `teacher_id`, `user_id`, `date_created`, `date_modified`) VALUES
('tsc1001', 'John', 'Doe', 12345678, '0712345678', 'john.doe@example.com', 'Mr.', '123 Main St', 'Nairobi', 100, 12345657, '1980-01-01', 21, 1, '2024-02-01 06:01:18', NULL),
('tsc1002', 'Jane', 'Smith', 23456785, '0712345679', 'jane.smith@example.com', 'Mrs.', '456 Elm St', 'Mombasa', 200, 23456788, '1985-02-02', 22, 2, '2024-02-01 06:01:18', NULL),
('tsc1003', 'Michael', 'Johnson', 34567895, '0712345680', 'michael.johnson@example.com', 'Dr.', '789 Pine St', 'Kisumu', 300, 34567890, '1990-03-03', 23, 3, '2024-02-01 06:01:18', NULL),
('tsc1004', 'Emily', 'Williams', 45678901, '0712345681', 'emily.williams@example.com', 'Ms.', '101 Oak St', 'Eldoret', 400, 45678901, '1982-04-04', 24, 4, '2024-02-01 06:01:18', NULL),
('tsc1005', 'David', 'Brown', 56789056, '0712345682', 'david.brown@example.com', 'Mr.', '202 Maple St', 'Nakuru', 500, 56789012, '1975-05-05', 25, 5, '2024-02-01 06:01:18', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `auth_table`
--
ALTER TABLE `auth_table`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD KEY `idx_auth_table_username` (`username`),
  ADD KEY `idx_auth_table_token` (`token`),
  ADD KEY `idx_auth_table_token_created_at` (`token_created_at`),
  ADD KEY `idx_auth_table_token_expires_at` (`token_expires_at`),
  ADD KEY `idx_auth_table_login_attempts` (`login_attempts`),
  ADD KEY `idx_auth_table_reset_key` (`reset_key`),
  ADD KEY `idx_auth_table_reset_key_expire_time` (`reset_key_expire_time`),
  ADD KEY `idx_auth_table_role` (`role`),
  ADD KEY `idx_auth_table_registration_status` (`registration_status`),
  ADD KEY `idx_auth_table_date_created` (`date_created`),
  ADD KEY `idx_auth_table_date_modified` (`date_modified`);

--
-- Indexes for table `class`
--
ALTER TABLE `class`
  ADD PRIMARY KEY (`class_id`),
  ADD UNIQUE KEY `class_name` (`class_name`),
  ADD KEY `idx_class_class_name` (`class_name`),
  ADD KEY `idx_class_date_created` (`date_created`),
  ADD KEY `idx_class_date_modified` (`date_modified`);

--
-- Indexes for table `exam`
--
ALTER TABLE `exam`
  ADD PRIMARY KEY (`exam_id`),
  ADD UNIQUE KEY `exam_name` (`exam_name`),
  ADD KEY `idx_exam_exam_name` (`exam_name`),
  ADD KEY `idx_exam_date_created` (`date_created`),
  ADD KEY `idx_exam_date_modified` (`date_modified`);

--
-- Indexes for table `exam_schedule`
--
ALTER TABLE `exam_schedule`
  ADD PRIMARY KEY (`exam_schedule_id`),
  ADD UNIQUE KEY `exam_id` (`exam_id`,`subject_id`,`class_id`,`month_year`),
  ADD KEY `subject_id` (`subject_id`),
  ADD KEY `teacher_id` (`teacher_id`),
  ADD KEY `class_id` (`class_id`),
  ADD KEY `idx_exam_schedule_exam_date` (`exam_date`),
  ADD KEY `idx_exam_schedule_month_year` (`month_year`),
  ADD KEY `idx_exam_schedule_date_created` (`date_created`),
  ADD KEY `idx_exam_schedule_date_modified` (`date_modified`);

--
-- Indexes for table `options`
--
ALTER TABLE `options`
  ADD PRIMARY KEY (`option_id`),
  ADD UNIQUE KEY `question_id` (`question_id`,`option_label`),
  ADD KEY `idx_options_date_created` (`date_created`),
  ADD KEY `idx_options_date_modified` (`date_modified`);

--
-- Indexes for table `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`question_id`),
  ADD KEY `exam_schedule_id` (`exam_schedule_id`),
  ADD KEY `idx_questions_date_created` (`date_created`),
  ADD KEY `idx_questions_date_modified` (`date_modified`);

--
-- Indexes for table `responses`
--
ALTER TABLE `responses`
  ADD PRIMARY KEY (`response_id`),
  ADD KEY `question_id` (`question_id`),
  ADD KEY `student_id` (`student_id`),
  ADD KEY `option_id` (`option_id`),
  ADD KEY `idx_responses_date_created` (`date_created`),
  ADD KEY `idx_responses_date_modified` (`date_modified`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`student_id`),
  ADD UNIQUE KEY `student_reg_number` (`student_reg_number`),
  ADD KEY `idx_student_firstname` (`firstname`),
  ADD KEY `idx_student_lastname` (`lastname`),
  ADD KEY `idx_student_gender` (`gender`),
  ADD KEY `idx_student_date_of_birth` (`date_of_birth`),
  ADD KEY `idx_student_guardian_name` (`guardian_name`),
  ADD KEY `idx_student_guardian_phone` (`guardian_phone`),
  ADD KEY `idx_student_class_id` (`class_id`),
  ADD KEY `idx_student_home_address` (`home_address`),
  ADD KEY `idx_student_city` (`city`),
  ADD KEY `idx_student_postal_address` (`postal_address`),
  ADD KEY `idx_student_student_reg_number` (`student_reg_number`),
  ADD KEY `idx_student_date_created` (`date_created`),
  ADD KEY `idx_student_date_modified` (`date_modified`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `subject`
--
ALTER TABLE `subject`
  ADD PRIMARY KEY (`subject_id`),
  ADD UNIQUE KEY `subject_name` (`subject_name`);

--
-- Indexes for table `teacher`
--
ALTER TABLE `teacher`
  ADD PRIMARY KEY (`teacher_id`),
  ADD UNIQUE KEY `tsc_number` (`tsc_number`),
  ADD UNIQUE KEY `id_number` (`id_number`),
  ADD UNIQUE KEY `phone` (`phone`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `national_insurance_number` (`national_insurance_number`),
  ADD KEY `idx_teacher_tsc_number` (`tsc_number`),
  ADD KEY `idx_teacher_id_number` (`id_number`),
  ADD KEY `idx_teacher_phone` (`phone`),
  ADD KEY `idx_teacher_email` (`email`),
  ADD KEY `idx_teacher_date_created` (`date_created`),
  ADD KEY `idx_teacher_date_modified` (`date_modified`),
  ADD KEY `user_id` (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `auth_table`
--
ALTER TABLE `auth_table`
  MODIFY `user_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `class`
--
ALTER TABLE `class`
  MODIFY `class_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `exam`
--
ALTER TABLE `exam`
  MODIFY `exam_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `exam_schedule`
--
ALTER TABLE `exam_schedule`
  MODIFY `exam_schedule_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `options`
--
ALTER TABLE `options`
  MODIFY `option_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=101;

--
-- AUTO_INCREMENT for table `questions`
--
ALTER TABLE `questions`
  MODIFY `question_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `responses`
--
ALTER TABLE `responses`
  MODIFY `response_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=151;

--
-- AUTO_INCREMENT for table `student`
--
ALTER TABLE `student`
  MODIFY `student_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `subject`
--
ALTER TABLE `subject`
  MODIFY `subject_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `teacher`
--
ALTER TABLE `teacher`
  MODIFY `teacher_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `exam_schedule`
--
ALTER TABLE `exam_schedule`
  ADD CONSTRAINT `exam_schedule_ibfk_1` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`exam_id`),
  ADD CONSTRAINT `exam_schedule_ibfk_2` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`subject_id`),
  ADD CONSTRAINT `exam_schedule_ibfk_3` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`teacher_id`),
  ADD CONSTRAINT `exam_schedule_ibfk_4` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`);

--
-- Constraints for table `options`
--
ALTER TABLE `options`
  ADD CONSTRAINT `options_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `questions` (`question_id`);

--
-- Constraints for table `questions`
--
ALTER TABLE `questions`
  ADD CONSTRAINT `questions_ibfk_1` FOREIGN KEY (`exam_schedule_id`) REFERENCES `exam_schedule` (`exam_schedule_id`);

--
-- Constraints for table `responses`
--
ALTER TABLE `responses`
  ADD CONSTRAINT `responses_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `questions` (`question_id`),
  ADD CONSTRAINT `responses_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`),
  ADD CONSTRAINT `responses_ibfk_3` FOREIGN KEY (`option_id`) REFERENCES `options` (`option_id`);

--
-- Constraints for table `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `student_ibfk_1` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`),
  ADD CONSTRAINT `student_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `auth_table` (`user_id`);

--
-- Constraints for table `teacher`
--
ALTER TABLE `teacher`
  ADD CONSTRAINT `teacher_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `auth_table` (`user_id`);
COMMIT;

