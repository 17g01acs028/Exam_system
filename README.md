
# Exam_sytem_api
This online exam API streamlines exam management and assessment for educational platforms, offering intuitive endpoints and automated grading features. It enhances efficiency and accessibility for educators and students alike.

# POSTMAN COLLECTION
Check out my API Documentation for detailed information on how to use the API.

# CONFIGURATION DESCRIPTION
Please create a configuration XML file (`config/config.xml`) with settings for both the database and API. Below is a breakdown of each section and its parameters:


<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<CONFIG>
    <DATABASE>
        <TYPE>
        <NAME>
        <HOST>
        <PORT>
        <USERNAME ENCRYPTED="NO">
        <PASSWORD ENCRYPTED="NO">
    </DATABASE>
    <API>
        <HOST>
        <PORT>
    </API>
</CONFIG>


| Parameter | Description                                      |
|-----------|--------------------------------------------------|
| `/CONFIG/DATABASE/TYPE`  | Specifies the type of the database (i.e., mysql, postgresql,mssql). |
| `/CONFIG/DATABASE/NAME`  | Specifies the name of the database.             |
| `/CONFIG/DATABASE/HOST`  | Specifies the host address of the database.     |
| `/CONFIG/DATABASE/PORT`  | Specifies the port number of the database.      |
| `/CONFIG/DATABASE/USERNAME` | Specifies the username used to authenticate to the database. The `ENCRYPTED` attribute indicates whether the username is encrypted or not (YES/NO). |
| `/CONFIG/DATABASE/PASSWORD` | Specifies the password used to authenticate to the database. The `ENCRYPTED` attribute indicates whether the password is encrypted or not (YES/NO). |
| `/CONFIG/API/HOST`  | Specifies the host address of the API.     |
| `/CONFIG/API/PORT`  | Specifies the port number of the API.      |

