grammar Expr;

prog: (decl | stmt)+                # Program
;

decl: ID ':' INT_TYPE '='  expr+          # Declaration
;


stmt: expr                              # ExpressionStatement
    | ifStmt                            # If
    | whileStmt                     # While
    | 'print' '(' expr ')' #PrintStatement // Print statement

;

ifStmt: 'if' expr 'then' expr ('else' expr)? 'break'  # IfElseStatement
;

whileStmt: 'while' stmt 'do' stmt+ 'break'         # WhileStatement
;



expr:
      '(' expr ')'                          # ParenthesizedExpr
    | ID                                    # Variable
    | NUM                                   # Number
    | expr ('*') expr                       # Multiplication
    | expr ('/') expr                       # Division
    | expr ('%') expr                       # Modulo
    | expr ('+') expr                       # Addition
    | expr ('-') expr                       # Subtraction
    | expr ('<') expr                       # LessThan
    | expr ('<=') expr                      # LessThanOrEqual
    | expr ('>') expr                       # GreaterThan
    | expr ('>=') expr                      # GreaterThanOrEqual
    | expr ('==') expr                      # Equal
    | expr ('!=') expr                      # NotEqual
    | expr ('&&') expr                      # LogicalAnd
    | expr ('||') expr                      # LogicalOr
    | expr ('=') expr                       # Equals
    | expr ('+=') expr                      # Increment
    | expr ('-=') expr                      # Decrement
    ;



ID: [a-z][a-zA-Z0-9_]*; // identifiers
NUM: '0' | '-'?[1-9][0-9]*; // numbers
INT_TYPE: 'INT'; // integer type
COMMENT: '--' ~[\r\n]* -> skip; // comments
WS: [ \r\t\n]+ -> skip; // whitespaces

