# csci-445-spring-2022-group
Coursework for CSCI 455  Embedded Systems: Robotics 

Coure Description: The basic tools and techniques of embedded systems using robotics as a platform. Student teams will build an autonomous mobile robot, and learn to program it to perform increasingly sophisticated behaviors. Besides providing an introduction to autonomous mobile robot technologies, the students also learn key concepts of mechanics, electronics, programming techniques, and systems design and integration


## Development  Instructions

### Projects File Structure

*** NEED TO DO ONCE WE START ***

```
csci-455-spring-2022-group  
│   README.md
│
└───SourceCode
│   │   
│   └───Robot
│       |   
│       └───temp
│      
└───RobotUnitTests
│       │   
│       └───temp
```


## Collaborating

### Cloning

The project can simply be cloned with `git clone git@github.com:patrickfoconnor/csci-455-spring-2022-group.git`

### Branching

Always develop on a branch besides main. The ideal formatting for a branch name is `initals_changedescription`. For example if John Deer is doing some refactors, his branch would be something like `jd_refactors`. However, as long as the branch name is descriptive of what is being changed, it's all good. A new branch can be created and switched to with `git checkout -b "branch name"`

### Adding and committing during development

During development it's best commit early and often. Commits should have descriptive messages. It's also best to have a small number of changes in each commit. The flow for adding changes and committing is:

- `git add .` to add all files or `git add file_or_directory` to add specific files/folders
- `git commit -m "message"` to commit changes

### Pushing new changes

Once you're ready to push changes, make sure you push your development branch using `git push origin "branch_name"`.

After pushing changes you're ready to open a pull request.

- Go to the pull requests tab
- Select "New Pull Request"
- Select "main" as the base branch and the branch you pushed as the compare branch
- Follow the format of [this template](https://github.com/patrickfoconnor/csci-455-spring-2022-group/blob/4faa9187733fd1e034044df3a0df83dfaa22097a/.github/pull_request_template.md) to begin describing the changes in your pull request
- Open your request and wait for it to be merged!


### Issues

Issues should simply be tracked through the [repository issue page](https://github.com/patrickfoconnor/csci-455-spring-2022-group/issues)
