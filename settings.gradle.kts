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
include("kotiki-micro")
include("kotiki-micro:controller")
findProject(":kotiki-micro:controller")?.name = "controller"
include("kotiki-micro:kotiki")
findProject(":kotiki-micro:kotiki")?.name = "kotiki"
include("kotiki-micro:owners")
findProject(":kotiki-micro:owners")?.name = "owners"
include("kotiki-micro:kotiki")
findProject(":kotiki-micro:kotiki")?.name = "kotiki"
include("kotiki:kitties")
findProject(":kotiki:kitties")?.name = "kitties"
include("kotiki:owners")
findProject(":kotiki:owners")?.name = "owners"
