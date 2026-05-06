# Contributing to Dynamic Workflow Engine

Thank you for your interest in contributing to the Dynamic Workflow Engine! We welcome contributions from the community.

## How to Contribute

### Reporting Bugs

If you find a bug, please create an issue with:
- A clear, descriptive title
- Steps to reproduce the issue
- Expected behavior
- Actual behavior
- Code samples or error messages
- Your environment details (Java version, OS, etc.)

### Suggesting Enhancements

Enhancement suggestions are tracked as GitHub issues. When creating an enhancement suggestion, please include:
- A clear, descriptive title
- A detailed description of the proposed enhancement
- Examples of how the enhancement would be used
- Why this enhancement would be useful

### Pull Requests

1. **Fork the repository**
2. **Create a feature branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

3. **Make your changes**
   - Follow the existing code style
   - Add tests for new functionality
   - Update documentation as needed

4. **Commit your changes**
   ```bash
   git commit -m "Add feature: description of your feature"
   ```

5. **Push to your fork**
   ```bash
   git push origin feature/your-feature-name
   ```

6. **Create a Pull Request**

## Development Setup

1. **Prerequisites**
   - Java 11 or higher
   - Maven 3.6 or higher

2. **Clone the repository**
   ```bash
   git clone https://github.com/your-org/DynamicWorkflowEngine.git
   cd DynamicWorkflowEngine
   ```

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run tests**
   ```bash
   mvn test
   ```

5. **Run examples**
   ```bash
   mvn exec:java -Dexec.mainClass="com.workflow.engine.example.WorkflowEngineExample"
   ```

## Code Style Guidelines

- Use 4 spaces for indentation (no tabs)
- Follow standard Java naming conventions
- Add Javadoc comments for public APIs
- Keep methods focused and concise
- Write meaningful variable and method names

## Testing

- Write unit tests for all new functionality
- Ensure all tests pass before submitting a PR
- Aim for high test coverage

## Documentation

- Update the README.md if you change functionality
- Add Javadoc comments to new public methods/classes
- Update INTEGRATION_GUIDE.md for integration-related changes
- Include code examples where appropriate

## Commit Messages

- Use the present tense ("Add feature" not "Added feature")
- Use the imperative mood ("Move cursor to..." not "Moves cursor to...")
- Limit the first line to 72 characters or less
- Reference issues and pull requests liberally after the first line

## Code Review Process

All submissions require review before being merged. We will:
- Review code for quality, style, and correctness
- Provide constructive feedback
- Request changes if needed
- Merge approved changes

## Community

- Be respectful and inclusive
- Follow the code of conduct
- Help others in discussions and issues

## Questions?

Feel free to create an issue with your question or reach out to the maintainers.

Thank you for contributing! 🎉
