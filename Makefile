.PHONY: help
help:
	@echo 'Make targets:'
	@echo '  build              Builds the project with tests and linting to create a new docker image'
	@echo '  help               Displays this help page'
	@echo '  test               Runs tests'

.PHONY: build
build:
		sh ./gradlew build -Dquarkus.package.jar.type=uber-jar

.PHONY: test
test:
	sh gradlew test
