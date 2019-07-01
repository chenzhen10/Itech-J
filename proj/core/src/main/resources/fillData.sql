INSERT INTO `kim`.`client` (`idclient`, `name`, `surname`, `last_name`, `gender`, `citizenship`) VALUES ('1', 'Dontez', 'Aaron', 'Yaytes', 'Male', 'Kansas-city');
INSERT INTO `kim`.`client` (`idclient`, `name`, `surname`, `last_name`, `gender`) VALUES ('2', 'Bruce', 'Marshall', 'Mathers', 'Male');
INSERT INTO `kim`.`client` (`idclient`, `name`, `surname`, `last_name`, `date`, `gender`) VALUES ('3', 'Romus', 'Sapkowsky', 'Zheznich', '1990-06-23', 'Male');
INSERT INTO `kim`.`client` (`idclient`, `name`, `surname`, `last_name`, `date`, `gender`, `citizenship`, `marital_status`, `web_site`, `email`, `workplace`, `country`, `city`, `street`, `house`, `num_of_house`, `index`) VALUES ('4', 'Rita', 'Kartonovna', 'Scxqw', '1995-05-23', 'Female', 'Belarus', 'Single', 'http:\\\\test.com', 'test123@gmail.com', 'EPAM', 'Belarus', 'Minsk', 'Dolgobrodskaya', '12/3', '113', '12354');


INSERT INTO `kim`.`phone` (`idphone`, `country_code`, `operator_code`, `number`, `type`, `commentary`, `idclient`) VALUES ('1', '375', '44', '4752403', 'Mobile', 'It\'s fake', '2');
INSERT INTO `kim`.`phone` (`idphone`, `country_code`, `operator_code`, `number`, `type`, `commentary`, `idclient`) VALUES ('2', '314', '25', '2345255', 'Home', 'AA', '3');
INSERT INTO `kim`.`phone` (`idphone`, `country_code`, `operator_code`, `number`, `type`, `commentary`, `idclient`) VALUES ('3', '134', '26', '2466666', 'Mobile', 'Abc', '1');



