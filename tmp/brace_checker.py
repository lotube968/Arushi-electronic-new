import re

def check_braces(filename):
    with open(filename, 'r', encoding='utf-8') as f:
        lines = f.readlines()
    
    stack = []
    for i, line in enumerate(lines):
        # strip comments to avoid counting braces inside strings or comments
        line_clean = re.sub(r'//.*$', '', line)
        line_clean = re.sub(r'".*?"', '', line_clean)
        
        for char in line_clean:
            if char == '{':
                stack.append((i + 1, '{'))
            elif char == '}':
                if not stack:
                    print(f"Extra '}}' on line {i+1}")
                else:
                    stack.pop()
                    
    if stack:
        print(f"Total unclosed braces: {len(stack)}")
        for line_num, char in stack[-5:]:
            print(f"Unclosed '{char}' opened on line {line_num}:")
            # print surrounding 2 lines of that line
            start = max(0, line_num - 2)
            end = min(len(lines), line_num + 3)
            for j in range(start, end):
                prefix = "->" if j == line_num - 1 else "  "
                print(f"  {j+1:4d}:{prefix} {lines[j].strip()}")

check_braces('/app/src/main/java/com/example/MainActivity.kt')
