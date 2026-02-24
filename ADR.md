# Architectural decisions record

## 3. Compose screens should be plain Composables, without and Scaffolds

### decision
* Don't use Scaffold on screen level Composables. There should be only one Scaffold that exists at navigation root level.

## 2. Within given feature - always connect UI layer to domain layer, and domain layer to data layer. Never connect UI to data directly.

### context
What are module/layer responsibilities?

### considered solutions
* ui -> domain -> data
* ui -> domain and data

### decision
* Always use domain layer use cases from UI layer and similar layers/modules. Never connect to data layer directly from ui and similar layers

### consequences
* Downside - more boilerplate
* Upside - very clean description of what the app can actually do

## 1. Modules that must be implemented as android module should be split between api and implementation modules

### context
* How to split modules? Which modules should be split between api and implementation?

### considered solutions
* Just implementation modules, public interface and everything else marked as internal
* Clean kotlin/java module with api and implementation android module

### decision
* Create clean kotlin api module and android implementation modules every time the implementation modules is an android module

### consequences
* Downside - more modules for little gain in initial stages of the project
* Upside - easier to change implementation in the future. Allows to have domain modules as clean kotlin/java module instead of forcing whole stack to be android modules
