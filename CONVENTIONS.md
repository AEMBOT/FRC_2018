# Liberty Robotics Code Guidelines (v3.1)
Jump to...
- [Line Breaks, Whitespace](#line-breaks-whitespace)
- [Conventions](#conventions)
- [Organization](#organization)
- [Version Control](#version-control)
- [Attribution](#attribution)

When writing any project code, please follow the following guidelines to maintain consistency.
## Line Breaks, Whitespace
Use Egyptian Style Braces { }, where the opening brace is on the same line as the declaration:
```
void someMethod() {
  //code
}
```
Put spaces on either side of an operator, EXCEPT with unary operators:
```
int a = b + c;
boolean d = e || f && g;

//DO NOT use a space with unary operators
a++;
d = !e;
```
Blocks should be logically seperated with line breaks as appropiate:
```
determineDirection();
moveToPoint();

prepareForAction();
```
In languages where pointers are declared (not Java), the `*` should be associated with the type, not the field.
```
int* ptr; // conforms
// as opposed to
int *ptr; // does not conform
```
## Conventions
Variables and methods should be typed in camelBack. Classes should be typed in PascalCase.
```
SystemsManager sysManager = new SystemsManager();
```
Constants which are only used in the local scope may be in all capitals. However, constants which are accessible members must be in PascalCase.
```
public final int SystemIdentifier = 0;

...

final int SYSTEM_ID = SystemsManager.SystemIdentifier;
```
In a `for` loop, `i` should be the primary iterator, `j` the secondary iterator if necessary, `k` the third, and so on...
```
for (int i = 0; i < sizeX; i++) {
  for (int j = 0; j < sizeY; j++) {
    //code
  }
}
```
Side Note: As seen above, your braces should close on the same level of indentation as their opening statement.

### Logical Detours
Code should be as straightforward as possible. Often, inexperienced programmers write code that is unneccessarily complicated, making it more time-consuming to be both understood and executed. The following are examples of some "novice mistakes" to be aware of.
#### Toggling a boolean
```
if (x == true) {
  x = false;
}

else if (x == false) {
  x = true;
}

//is equivalent to...

x = !x // much cleaner, and takes one instruction instead of several.
```
#### Setting a variable
This one happens more than it should.
```
if (x != 0) {
  x = 0;
}

// the above is actually more expensive than...
x = 0;
```
## Organization
In python, all functions should be defined at the top of the file, BEFORE the main block.
```
def a():

def b():

a()
b()
```
In classes, accessors and mutators should be defined at the end of the class, getter then setter, in the order that the members are declared. 
```
public class Example {
  private int x;
  private double y, z;
  
  public Example() {}
  
  private doSomething() {}
  
  public int getX() {}
  
  public void setX(int x) {}
  
  public double getY() {}
  
  public void setY(double y) {}
  
  public double getZ() {}
  
  public void setZ (double z) {}
}
  ```
## Version Control
We use git hosted on GitHub for Version Control. To effectively use this tool it is important to stay organised and consistent in versioning.
### Commits
Commits should document changes in code that represent milestones in work. It is better to commit too often than to not commit often enough. Commits should encapsulate a single task - this makes it easier to track changes to certain features over time.

Commit names should be a single sentence summarising the effect of the changes in the commit. Commit descriptions are optional and to be used if necessary.
### Branches
Each feature should be contained in a separate branch as to not collide with the work of others. Branches are to be named after their purpose. Branch names are to be all lowercase with `-` used as a word separator when necessary.
* Good branch names: `ball-shooter`, `optimize`, `remove-deprecated`
* Bad branch names: `Xx_dustin's_branch_xX`, `branch1`,`this-is-broken`

### Merging
In order to encourage collaboration, all merging is to be done in the form of pull requests, which are to be reviewed and improved by others. Competition code is to reside on the ```master``` branch and should be competition-ready at all times. The master branch is to be locked, with the only way to change it being through reviewed and approved merge requests.
## Attribution
"Cite your sources!". No, really. FIRST allows (even encourages) sharing code. Make sure to respect the licenses of all public code, and it goes without saying that you should provide attribution to any adopted blocks. This can be as simple as commenting a URL with the source (Yes, cite StackOverflow if you steal a StackOverflow example, although that is less likely to come up in robotics). Changing the names of tokens (variables, method names, etc) does not magically make it your code!
