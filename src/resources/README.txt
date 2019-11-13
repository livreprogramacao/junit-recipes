SETTING UP THE COFFEE SHOP DATABASE
===================================

There are two basic steps to creating the database.

1. Create the Mimer database using Mimer's toolset
2. Execute the SQL/DDL scripts

Creating the Mimer database
---------------------------

Download and install Mimer from http://www.mimer.se. This database
system is freely available for development with up to 10 concurrent
users, although there is always the chance that Upright Database
Technologies' policies have changed, so check the web site for details.

Launch the Mimer Administrator. On Windows, there is a shortcut from
your start Menu. The corresponding executable is called 'mimadmin', so
look for the executable or a script in your Mimer installation directory
on UNIX.

In the Administrator, choose Add.... The Coffee Shop scripts expect you
to create a database named 'coffeeShopData', so specify that as the
name. Even if the name is not case sensitive, it is good practice to
assume that it is. (Since a database name corresponds roughly to a file
system directory, then it is case sensitive on UNIX, but not Windows.
Even so, assume that everything is case sensitive. It makes your life
easier.)

By default, a Mimer database uses approximately 150 MB of RAM at
runtime. To reduce this, follow the advice at this web page

    http://www.diasparsoftware.com/tips-and-tricks.html
    
Look for the mini-article entitled "Mimer Database Administration".

When specifying the system administrator username and password, choose
'sysadm' for both.

When you have finished specifying the database settings, press OK and
follow the instructions while Mimer creates the database. Once this
completes, you are ready to execute the SQL/DDL scripts.


Execute the SQL/DDL scripts
---------------------------

The scripts create users, assign permissions, create tables and stored
procedures. Unfortunately, they need to be executed in a certain order.
Fortunately, we have provided a Windows script to do exactly that. You
can use the Windows script to create the corresponding UNIX script for
your system, if you need it. (Sorry -- we only needed to execute the
code on Windows.)

Execute the script create-database-windows from a Command Prompt. Note
that the Mimer tool 'bsql' (Batch SQL executer) will need to be on your
system path to execute this script. The Mimer installation should have
done this for you; but if it did not, then add the Mimer installation
directory to your system PATH environment variable.


Verify creating the database
----------------------------

From a Command Prompt, issue this command

    bsql -u admin -p adm1n coffeeShopData
    
This launches a BSQL prompt connected to the newly-created database. Now
look for the tables that the scripts created. Issue this command

    SQL> list tables;
    
Note that BSQL command must end with a semicolon. Expect at least the
following tables.

    CATALOG.BEANS
    CATALOG.DISCOUNT
    CATALOG.DISCOUNTDEFINITION
    ORDERS.ORDERITEM
    ORDERS.ORDERS
    PEOPLE.CUSTOMER

There are additional tables, but they are system tables and you can
ignore them. The tables listed previously should be empty, so do not be
worried if you find no data in them.

Congratulations! You have successfully created the Coffee Shop
application database.

