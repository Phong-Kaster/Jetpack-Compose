## Agent skills

### Issue tracker

Issues live in GitHub Issues on this repo. See `docs/agents/issue-tracker.md`.

### Triage labels

Default triage label vocabulary (needs-triage, needs-info, ready-for-agent, ready-for-human, wontfix). See `docs/agents/triage-labels.md`.

### Domain docs
Single-context layout: one `CONTEXT.md` + `docs/adr/` at the repo root. See `docs/agents/domain.md`.


## General Behavior

- Be concise and direct.
- Prioritize actionable output over explanations.
- Do not narrate your thinking process.
- Do not explain what you are about to do.
- Do not restate the user's request.
- Avoid motivational or conversational filler.
- Avoid repeating information already shown.
- Avoid long summaries after completing tasks.

---

## Token Efficiency Rules

- Prefer minimal responses.
- Prefer compact diffs over full-file output.
- Do not print unchanged code.
- Do not echo large code blocks unless requested.
- Do not repeat imports unless relevant.
- Avoid verbose UI descriptions.
- Avoid step-by-step narration.
- Avoid repeating previous edits in summaries.

### Forbidden verbosity patterns

Avoid phrases like:
- "Now let me..."
- "Here's what I implemented..."
- "I will now..."
- "The following changes were made..."
- "This matches the Figma..."
- "The build is clean."

---

## Output Format

Unless explicitly requested otherwise, respond using:

Changed:
- file1
- file2

Result:
- success / failed

Issues:
- none

If code changes are needed:
- show only relevant diffs
- prefer unified diff format
- avoid full file rewrites

---

## Build & Errors

- Build silently.
- Report only:
    - build status
    - relevant compiler errors
    - affected files
- Do not paste full Gradle logs.
- Do not include warnings unless relevant.
- Stop after the first meaningful compilation blocker.

---

## Kotlin Rules

- Follow clean architecture.
- Prefer immutable UI state.
- Use descriptive naming.
- Keep composables small and focused.
- Avoid unnecessary abstractions.
- Avoid premature optimization.
- Prefer explicit state handling.
- Prefer readability to cleverness.