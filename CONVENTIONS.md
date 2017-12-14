# Liberty Robotics Code and Git Guidelines (v4)
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
**SMB's Thoughts**: If a function has too many parameters, break the list across lines, and put the curly brace on a line by itself.
```
void some_method( int a,
                  int b,
                  int c,
                  ...)
{
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
**SMB's Thoughts**: Organize expressions in a self-documenting way, and for readibility. Don't be rigid. Long expressions can be extra long with too many spaces. Rule of thumb: spaces around binary + and -, no spaces for binary * and /, which parallels their precedence. Break
calculations into subexpressions using temporary variables.

Blocks should be logically seperated with line breaks as appropiate:

**SMB's Thoughts**: Yes! Absolutely.
```
determineDirection();
moveToPoint();

prepareForAction();
```
In C where pointers are declared (not Java), the `*` should be associated with the variable.
```
int *ptr; // conforms
// as opposed to
int* ptr; // does not conform
```
## Conventions
Variables and methods should be typed in camelBack. Classes should be typed in PascalCase.
```
SystemsManager sysManager = new SystemsManager();
```
**SMB's Thoughts**
Use names that are as most like natural English: capital acronyms, underscores_for_spaces when spaces are not allowed, which allows for natural hyphenation, function names with underscores (more readable like spaces in English). Don't be too rigid. Let go of rules when they don't work so well. (Computer programmers sometimes have a psyhology of rigid rules like programming languages. The computer is rigid, but you're human.)

Constants which are only used in the local scope may be in all capitals. However, constants which are accessible members must be in PascalCase.
```
public final int SystemIdentifier = 0;

...

final int SYSTEM_ID = SystemsManager.SystemIdentifier;
```

**SMB's Thoughts**: Constants always all caps (except in rare cases where it might be natural to use some lower case letters).

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

SMB's Thoughts: I don't like Python.

  ```
## Version Control
We use git hosted on GitHub for Version Control. To effectively use this tool it is important to stay organised and consistent in versioning.

### Commits
When you're committing at a milestone or other significant point, document your commit well. Otherwise, commit and PUSH often in order to keep your code backed up, and available to everyone.

Commits should be in good English, succinct, and effective. For regular commits, something like this is fine: "Saving work, not compiling". For commits at significant point, list the signicant items in a bullet list, and add details as necessary.

### Branches

Each team member should have their own branch in order to be able to experiment (poo around) without affecting other people's work. Git features allow you to combine code/branches in any way conveniently, so having separate branches does not hinder you at all. It also allows people to claim their code when it's beautiful, and makes them accountable when it's not--without having to search through commits.

The master branch should be protected, and only updated when code is reviewed and approved.

### Merging

In order to encourage collaboration, all merging is to be done in the form of pull requests, which are to be reviewed and improved by others. Competition code is to reside on the ```master``` branch and should be competition-ready at all times. The master branch is to be locked, with the only way to change it being through reviewed and approved merge requests.

## Attribution
"Cite your sources!". No, really. FIRST allows (even encourages) sharing code. Make sure to respect the licenses of all public code, and it goes without saying that you should provide attribution to any adopted blocks. This can be as simple as commenting a URL with the source (Yes, cite StackOverflow if you steal a StackOverflow example, although that is less likely to come up in robotics). Changing the names of tokens (variables, method names, etc) does not magically make it your code!

# Working Directory Organization

Features should be logically organized into sub-directories.

# Mechanical Coding Standards

If your standards can be put into code--don't require any human judgment, use a program to make your code follow the standards if such program is available (such as lint for C).

# Importance of Standards and Guidelines

Larger projects with larger teams lasting over longer time periods need more standards and guidelines. Standards and guidelines should help, not hinder your development. Match your project with your overhead in using standards and guidelines.
