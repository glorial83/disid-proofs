This proof is made up to check the posibility to define a data source when
application is already started.


The main technical points are:

* Define an initial Datasource conection which helps to initialize all
  artificats. This connection could be a in-memory database.
* Define a bean which holds and manages DataSource configuration.
* Define a custom Datasource which returns the efective Datasource to use:
  Returns the initila until the work-database-definition is complete.
* Define a view and its controllers which prompt for DS configuration to user.
* Define a MVC Interceptor which redired all request to configuration view
  until it is completed.


The artifacts affected are:

* "application.properties"
** Datasource common definition
** Initial datasource Definition
** Hibernate DDL set to "create"
* "DataSourceConfigurationValue"
** Component which manages DS configuration
* "RuntimeConfiguredDatasource"
** DataSource implementation which creates DataSource instances based on
   "DataSourceConfigurationValue" information
* "DataSourceConfiguration"
** Register "RuntimeConfiguredDatasource"
* "datasource.html"
** View with form to configure DS
* "DatasourceConfigurationController"
** Shows and handles "datasouce.html" view request
* "DatasourceConfigurationInterceptor"
** Interceptor which redireds request to "DatasourceConfigurationController"
   while configuration is not set
