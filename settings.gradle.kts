rootProject.name = "alinaiil"

include("banks")
include("banks-console")
include("kotiki")
include("kotiki:service")
findProject(":kotiki:service")?.name = "service"
include("kotiki:data")
findProject(":kotiki:data")?.name = "data"
include("kotiki:controller")
findProject(":kotiki:controller")?.name = "controller"
