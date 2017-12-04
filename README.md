# iox9
Framework for user flows monitoring in establishments.

## Branches organization
``master``: the IOx9 framework release branch

``iox9-dev``: the IOx9 framework development branch

``rapx9-dev``: the RAPx9 application development branch

## Framework workflow

You have things that capture data which contain an identification object for the entity you want to monitor. If you want to monitor cars, for example, the capturing thing can be a camera and the identification object can be the car's license plate. In order to recognize the identification object, you generally use an algorithm. In the car case, a license plate recognition algorithm. Also, you want to store the entering and the exiting of the entity in a database, as well as general and specific informations about each entity. Finally, you want to fire notifications, generate statistics and show the entities' data for the final user.

If you are in this scenario, this is the right framework for you.

## Usage

1. Choose your capturing things and implement code that takes the data from the things, using the IdentityDataSource abstract class.
2. Choose the algorithm for extracting identification objects from the captured data, implementing the EntityRecognizer.
3. Implement processors for this data, extending the abstract class EntityProcessor, which contains a template method.
4. Decide if you want to capture extra data from external sources, and, if so, implement the InformationCollector.
5. Choose the statistics you want to get, and for each of them, implement a class which extends from StatisticsProcessor.
6. Decide the notifications you are interested, and for ech of them, implement a class which extends from NotificationAgent.
7. Implement classes which are interested in informations from the processors, statistics processors and notification agents.
8. In the configx9.yaml configuration file, configure the specific data for your application.
