## Project Technology Stack

- Java 21
- Spring Boot 4.0.6
- Gradle 9.4.1
- Spring Boot Gradle Plugin 4.0.6
- Spring Dependency Management Plugin 1.1.7

### Main Dependencies

- Spring Boot Starter WebMVC
- Spring Boot Starter Data JPA
- Spring Boot Starter Validation
- Spring Boot Starter Actuator
- Spring Boot H2Console
- Lombok
- H2 Database
- MySQL

### Test Dependencies

- Spring Boot Starter Actuator Test
- Spring Boot Starter Data JPA Test
- Spring Boot Starter Validation Test
- Spring Boot Starter WebMVC Test
- JUnit Platform Launcher
- Lombok


## Commands
- Run all tests: `./gradlew test`
- Build application: `./gradlew build`

## Basic Rules
- Do not run `./gradlew **` unless specified to do so.
- Main agents should orchestrate the workflow and delegate tasks to subagents as needed.
- Subagents should focus on their specific roles (service-developer, project-tester, project-reviewer) and avoid stepping into tasks outside their scope.
- Communication between main agents and subagents should be clear and concise, with regular updates on progress and any issues encountered.

## Subagents Operation Rules

### Before Spawning Subagents:
- Main agents should evaluate the current workload and task requirements before spawning subagents.
- Consider the complexity and interdependencies of tasks to determine if subagents are necessary.
- Ensure that the main agent has sufficient context and information to effectively delegate tasks to subagents.
- TDD approach should be followed, and subagents should be spawned to assist in writing tests or implementing features as needed.

### Subagent Spawning Strategy

**Basic Rules:**
- Spawn 2 subagents maximum for each service-developer, project-tester and project-reviewer.
- If more subagents are needed, pause the action item until a thread is available.
- All subagents must process action items in parallel but should not exceed the maximum limit.
- Subagents should work in sync with the PM subagents and report progress regularly.
- If necessary main agents have right to control all subagents working in serial process.

**Continue Using Same Subagent When:**
- Action items are related or sequential (same feature/task)
- Context from previous tasks is needed
- Working on the same problem domain

**Create New Subagent When:**
- Switching to a completely different task type
- Previous subagent has completed all action items
- Need specialized expertise from a different agent
- Starting a new major work item

**Closing Subagents:**
- Close subagents when all assigned action items are complete
- Do not keep subagents running unnecessarily
- Ensures resource efficiency and clear task boundaries

### Use `service-developer`
When Action Item involves:
- Writing or modifying production code
- Implementing new features
- Refactoring existing code

Use Custom Agent:
- `.codex/agents/service-developer.toml`

### Use `project-tester`
When Action Item involves:
- Writing or modifying test code
- Implementing new test cases
- Refactoring existing tests

Use Custom Agent:
- `.codex/agents/project-tester.toml`

### Use `project-reviewer`
When Action Item involves:
- Reviewing code changes
- Providing feedback on code quality
- Ensuring adherence to coding standards
Use Custom Agent:
- `.codex/agents/project-reviewer.toml`

## Output Expectations
- Subagents should provide clear and concise updates on their progress.
- Any issues or blockers encountered should be reported immediately to the main agent.
- Results should print in detail, numbering by action item, and summary table list of total outcome results.
- All printed output in terminal should be written in Korean.

